package org.eclipse.graphiti.examples.chess;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.examples.chess.diagram.ChessDiagramTypeProvider;
import org.eclipse.graphiti.ui.editor.inject.DiagramModule;

import com.google.inject.Binder;

public class ChessModule extends DiagramModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);

		binder.bind(IDiagramTypeProvider.class).to(ChessDiagramTypeProvider.class);
	}

}
