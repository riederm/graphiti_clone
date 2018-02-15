package org.eclipse.graphiti.ui.editor.inject;


import org.eclipse.graphiti.notification.DefaultNotificationService;
import org.eclipse.graphiti.notification.INotificationService;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;

import com.google.inject.Binder;
import com.google.inject.Module;

public class DiagramModule implements Module {

	@Override
	public void configure(Binder binder) {
		// binder.bind(DiagramBehavior.class).to(getDiagramBehaviourBinding());
		binder.bind(IToolBehaviorProvider.class).to(getToolBehaviorProviderBinding());
		binder.bind(INotificationService.class).to(getNotificationServiceBinding());
	}

	protected Class<? extends INotificationService> getNotificationServiceBinding() {
		return DefaultNotificationService.class;
	}

	protected Class<? extends DiagramBehavior> getDiagramBehaviourBinding() {
		return DiagramBehavior.class;
	}

	protected Class<? extends IToolBehaviorProvider> getToolBehaviorProviderBinding() {
		return DefaultToolBehaviorProvider.class;
	}

}
