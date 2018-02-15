package org.eclipse.graphiti.ui.editor.inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public abstract class DiagramExecutableFactory implements IExecutableExtensionFactory, IExecutableExtension {

	protected String clazzName;
	protected IConfigurationElement config;


	@Override
	public Object create() throws CoreException {
		Class<?> c;
		try {
			c = getBundle().loadClass(clazzName);
			return getInjector().getInstance(c);
		} catch (ClassNotFoundException e) {
			throw new CoreException(new Status(Status.ERROR, getBundle().getSymbolicName(), e.getMessage(), e));
		}
	}

	abstract protected Bundle getBundle();

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {

		if (data instanceof String) {
			clazzName = (String) data;
		} else {
			throw new IllegalArgumentException("couldn't handle passed data : " + data);
		}
		this.config = config;
	}

	abstract protected Injector getInjector();

}
