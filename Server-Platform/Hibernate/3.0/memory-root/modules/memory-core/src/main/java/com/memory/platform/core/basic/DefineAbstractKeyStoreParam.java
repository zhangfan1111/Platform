package com.memory.platform.core.basic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.schlichtherle.license.KeyStoreParam;

public abstract class DefineAbstractKeyStoreParam implements KeyStoreParam {
	private final Class clazz;
    private final String resource;

    /**
     * Creates a new instance of AbstractKeyStoreParam which will look up
     * the given resource using the class loader of the given class when
     * calling {@link #getStream()}.
     * 
     * @param clazz the class which refers to the class loader to use.
     * @param resource the resource to look up.
     */
    protected DefineAbstractKeyStoreParam(final Class clazz, final String resource) {
        if (null == clazz || null == resource)
            throw new NullPointerException();
        this.clazz = clazz;
        this.resource = resource;
    }

    /**
     * Looks up the resource provided to the constructor using the classloader
     * provided to the constructor and returns it as an {@link InputStream}.
     */
    public InputStream getStream() throws IOException {
    	//change getClassLoader
        final InputStream in = clazz.getClassLoader().getResourceAsStream(resource);
        if (null == in)
            throw new FileNotFoundException(resource);
        return in;
    }

    /**
     * Returns {@code true} if and only if these key store parameters seem to
     * address the same key store entry as the given object.
     * 
     * @param  object the object to compare.
     * @return {@code true} if and only if these key store parameters seem to
     *         address the same key store entry as the given object.
     */
    public final boolean equals(final Object object) {
        if (!(object instanceof DefineAbstractKeyStoreParam))
            return false;
        final DefineAbstractKeyStoreParam that = (DefineAbstractKeyStoreParam) object;
        return this.clazz.equals(that.clazz)
                && this.resource.equals(that.resource)
                && this.getAlias().equals(that.getAlias());
    }

    /**
     * Returns a hash code which is consistent with {@link #equals(Object)}.
     * 
     * @return A hash code which is consistent with {@link #equals(Object)}.
     */
    public final int hashCode() {
        int c = 17;
        c = 37 * c + this.clazz.hashCode();
        c = 37 * c + this.resource.hashCode();
        c = 37 * c + this.getAlias().hashCode();
        return c;
    }
}
