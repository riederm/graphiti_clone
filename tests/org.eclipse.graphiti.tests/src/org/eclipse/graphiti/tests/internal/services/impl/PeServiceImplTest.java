/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2013, 2018 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    mwenz - Bug 421813 - Relative position to diagram of active Shape nested in inactive ContainerShape is calculated incorrectly
 *    mwenz - Bug 529378 - PeServiceImpl.getConnectionMidpoint may return incorrect value when connection has no bendpoints
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.graphiti.tests.internal.services.impl;

import static org.junit.Assert.assertEquals;

import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.internal.services.impl.PeServiceImpl;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ICreateService;
import org.eclipse.graphiti.services.IGaService;
import org.junit.Test;

@SuppressWarnings("restriction")
public class PeServiceImplTest {

	private PeServiceImpl peService = (PeServiceImpl) Graphiti.getPeService();

	private ICreateService createService = Graphiti.getCreateService();
	private IGaService gaService = Graphiti.getGaService();

	@Test
	public void getLocationRelativeToDiagram_singleGa() throws Exception {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);
		ContainerShape containerShape = createService.createContainerShape(diagram, true);
		Rectangle rectangle = createService.createRectangle(containerShape);
		gaService.setLocationAndSize(rectangle, 10, 20, 100, 100);

		ILocation location = peService.getLocationRelativeToDiagram(containerShape);
		assertEquals(10, location.getX());
		assertEquals(20, location.getY());
	}

	@Test
	public void getLocationRelativeToDiagram_twoGas() throws Exception {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);
		ContainerShape containerShape = createService.createContainerShape(diagram, true);
		Rectangle rectangle = createService.createRectangle(containerShape);
		gaService.setLocationAndSize(rectangle, 10, 20, 100, 100);
		ContainerShape containerShape2 = createService.createContainerShape(containerShape, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 5, 15, 50, 50);

		ILocation location = peService.getLocationRelativeToDiagram(containerShape2);
		assertEquals(15, location.getX());
		assertEquals(35, location.getY());
	}

	@Test
	public void getLocationRelativeToDiagram_twoGasWithInactive() throws Exception {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);
		ContainerShape containerShape = createService.createContainerShape(diagram, false);
		Rectangle rectangle = createService.createRectangle(containerShape);
		gaService.setLocationAndSize(rectangle, 10, 20, 100, 100);
		ContainerShape containerShape2 = createService.createContainerShape(containerShape, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 5, 15, 50, 50);

		ILocation location = peService.getLocationRelativeToDiagram(containerShape2);
		assertEquals(5, location.getX());
		assertEquals(15, location.getY());
	}

	@Test
	public void getLocationRelativeToDiagram_inactiveGaAtRoot() throws Exception {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);
		ContainerShape containerShape = createService.createContainerShape(diagram, false);
		Rectangle rectangle = createService.createRectangle(containerShape);
		gaService.setLocationAndSize(rectangle, 50, 50, 100, 100);

		ILocation location = peService.getLocationRelativeToDiagram(containerShape);
		assertEquals(50, location.getX());
		assertEquals(50, location.getY());
	}

	@Test
	public void getConnectionMidpoint_rootShapesUpLeftToDownRight() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 100, 100, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 200, 200, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpoint_rootShapesDownRightToUpLeft() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 200, 200, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 100, 100, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpoint_rootShapesDownLeftToUpRight() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 100, 200, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 200, 100, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpoint_rootShapesUpRightToDownLeft() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 200, 100, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 100, 200, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpointWithTwoBendpoints_rootShapesUpLeftToDownRight() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 100, 100, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 200, 200, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);
		connection.getBendpoints().add(createService.createPoint(130, 130));
		connection.getBendpoints().add(createService.createPoint(170, 170));

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpointWithTwoBendpoints_rootShapesDownRightToUpLeft() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 200, 200, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 100, 100, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);
		connection.getBendpoints().add(createService.createPoint(170, 170));
		connection.getBendpoints().add(createService.createPoint(130, 130));

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(155, midpoint.getX());
		assertEquals(155, midpoint.getY());
	}

	@Test
	public void getConnectionMidpointWithTwoBendpoints_rootShapesDownLeftToUpRight() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 100, 200, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 200, 100, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);
		connection.getBendpoints().add(createService.createPoint(130, 170));
		connection.getBendpoints().add(createService.createPoint(170, 130));

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(150, midpoint.getX());
		assertEquals(150, midpoint.getY());
	}

	@Test
	public void getConnectionMidpointWithTwoBendpoints_rootShapesUpRightToDownLeft() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape containerShape1 = createService.createContainerShape(diagram, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 200, 100, 10, 10);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(diagram, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 100, 200, 10, 10);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);
		connection.getBendpoints().add(createService.createPoint(170, 130));
		connection.getBendpoints().add(createService.createPoint(130, 170));

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(150, midpoint.getX());
		assertEquals(150, midpoint.getY());
	}

	@Test
	public void getConnectionMidpoint_bug529378() {
		Diagram diagram = createService.createDiagram("dummyType", "diagramName", true);

		ContainerShape rootShape = createService.createContainerShape(diagram, true);
		Rectangle rectangle = createService.createRectangle(rootShape);
		gaService.setLocationAndSize(rectangle, 60, 30, 469, 209);

		ContainerShape containerShape1 = createService.createContainerShape(rootShape, true);
		Rectangle rectangle1 = createService.createRectangle(containerShape1);
		gaService.setLocationAndSize(rectangle1, 50, 170, 152, 31);
		ChopboxAnchor anchor1 = createService.createChopboxAnchor(containerShape1);

		ContainerShape containerShape2 = createService.createContainerShape(rootShape, true);
		Rectangle rectangle2 = createService.createRectangle(containerShape2);
		gaService.setLocationAndSize(rectangle2, 40, 30, 159, 31);
		ChopboxAnchor anchor2 = createService.createChopboxAnchor(containerShape2);

		FreeFormConnection connection = createService.createFreeFormConnection(diagram);
		connection.setStart(anchor1);
		connection.setEnd(anchor2);

		ILocation midpoint = peService.getConnectionMidpoint(connection, 0.5d);

		assertEquals(183, midpoint.getX());
		assertEquals(145, midpoint.getY());
	}
}
