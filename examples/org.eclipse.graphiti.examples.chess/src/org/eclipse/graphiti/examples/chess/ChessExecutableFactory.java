package org.eclipse.graphiti.examples.chess;

import org.eclipse.graphiti.ui.editor.inject.DiagramExecutableFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class ChessExecutableFactory extends DiagramExecutableFactory {

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return Activator.getDefault().getInjector();
	}
}
