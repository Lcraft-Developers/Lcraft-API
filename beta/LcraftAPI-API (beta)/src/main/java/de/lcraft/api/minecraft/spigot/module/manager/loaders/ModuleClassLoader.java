package de.lcraft.api.minecraft.spigot.module.manager.loaders;

import com.google.common.io.ByteStreams;
import de.lcraft.api.minecraft.spigot.module.manager.ModuleDescriptionFileManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ModuleClassLoader extends URLClassLoader {
    // ClassLoader Manager

    public static ArrayList<ClassLoader> classLoaders = new ArrayList<>();
    static {
        ClassLoader.registerAsParallelCapable();
    }

    // Normal Classloader

    private JarFile jar;
    private URL url;
    private Manifest manifest;

    protected ModuleClassLoader(File file) throws MalformedURLException {
        super(new URL[]{ file.toURI().toURL() });
        try {
            jar = new JarFile(file);
            this.url = file.toURI().toURL();
            classLoaders.add(this);
            this.manifest = jar.getManifest();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return loadClass0( name, resolve, true, true );
    }
    private final Class<?> loadClass0(String name, boolean resolve, boolean checkOther, boolean checkLibraries) throws ClassNotFoundException {
        try {
            Class<?> result = super.loadClass( name, resolve );

            // Library classes will appear in the above, but we don't want to return them to other plugins
            if (checkOther || result.getClassLoader() == this) {
                return result;
            }
        } catch (ClassNotFoundException ex) {}

        if (checkOther) {
            for (ClassLoader loader : classLoaders ) {
                if ( loader != this ) {
                    try {
                        return loader.loadClass(name);
                    } catch ( ClassNotFoundException ex ) {}
                }
                continue;
            }
        }

        throw new ClassNotFoundException( name );
    }
    @Override
    protected final Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name.replace( '.', '/' ).concat( ".class" );
        JarEntry entry = jar.getJarEntry( path );

        if ( Objects.nonNull(entry) ) {
            byte[] classBytes;

            try ( InputStream is = jar.getInputStream( entry ) ) {
                classBytes = ByteStreams.toByteArray( is );
            } catch ( IOException ex ) {
                throw new ClassNotFoundException( name, ex );
            }

            int dot = name.lastIndexOf( '.' );
            if ( dot != -1 ) {
                String pkgName = name.substring( 0, dot );
                if ( getPackage( pkgName ) == null ) {
                    try {
                        if ( Objects.nonNull(manifest) ) {
                            definePackage( pkgName, manifest, url );
                        } else {
                            definePackage( pkgName, null, null, null, null, null, null, null );
                        }
                    } catch ( IllegalArgumentException ex ) {
                        if ( getPackage( pkgName ) == null ) {
                            throw new IllegalStateException( "Cannot find package " + pkgName );
                        }
                    }
                }
            }

            CodeSigner[] signers = entry.getCodeSigners();
            CodeSource source = new CodeSource( url, signers );

            return defineClass( name, classBytes, 0, classBytes.length, source );
        }

        return super.findClass( name );
    }

    public static ArrayList<ClassLoader> getClassLoaders() {
        return classLoaders;
    }
    public final JarFile getJar() {
        return jar;
    }
    public final Manifest getManifest() {
        return manifest;
    }
    public final URL getUrl() {
        return url;
    }
    @Override
    public final URL[] getURLs() {
        return super.getURLs();
    }

}
