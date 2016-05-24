package com.robot.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.robot.model.Robot;
import com.robot.service.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RobotTest {

	public String placeCommand = "PLACE 0,0,SOUTH";

	// public RobotService cwd = new RobotService("PLACE 0,0,SOUTH", new
	// Robot());

	@Test
	public void testData() {
		try {
			List<String> listOfParam = new ArrayList<String>();

			listOfParam.add("PLACE 0,0,NORTH");
			listOfParam.add("MOVE");
			listOfParam.add("REPORT");
			listOfParam.add("LEFT");
			listOfParam.add("REPORT");

			listOfParam.add("   PLACE     1   ,     2   ,     EAST   ");
			listOfParam.add("   MOVE   ");
			listOfParam.add("   REWIND   "); // Test case for checking wrong
												// move. It continues even after any wrong move.
			listOfParam.add("   LEFT");
			listOfParam.add("   MOVE   ");
			listOfParam.add("   REPORT   ");

			listOfParam.add("   PLACE     2   ,     2   ,     EAST   ");
			listOfParam.add("   REWIND   "); // Test case for checking wrong
												// move. It continues even after any wrong move.
			listOfParam.add("   MOVE   ");
			listOfParam.add("   LEFT ");
			listOfParam.add("   MOVE   ");
			listOfParam.add("   RIGHT   ");
			listOfParam.add("   REPORT   ");

			RobotService cwd = new RobotService(placeCommand, new Robot());
			cwd.action(listOfParam);
		} catch (Exception ee) {
			fail(ee.getMessage());
		}

	}

	/* Test if the first command is only PLACE command */
	@Test
	public final void testIfFirstCommandIsPlaceCommand() {
		try {
			new RobotService(placeCommand, new Robot());
		} catch (Exception ee) {
			fail(ee.getMessage());
		}
	}

	/*
	 * This Test case checks if first command is PLACE command or not. In
	 * Parallel, this test case validates if the place command has first five
	 * letters as "PLACE"
	 */
	@Test
	public final void testForValidPlaceCommandAlsoIfFirstCommandIsPlaceCommand() {
		try {
			RobotService cwd = new RobotService(placeCommand, new Robot());
			Assert.assertEquals(true, cwd.isValidPlaceCommand(placeCommand));
		} catch (Exception ee) {
			fail(ee.getMessage());
		}
	}

	/*
	 * This test case validates if the coordinates in place command are valid.
	 * It checks if X and Y coordinates are integers and direction matches one
	 * of the (WEST,EAST,SOUTH,NORTH)
	 */

	@Test
	public final void testForValidCoordinatesForPlaceCommand() {
		try {
			RobotService cwd = new RobotService(placeCommand, new Robot());
			cwd.setCoordinatesForPlaceCommand(placeCommand);
		} catch (Exception ee) {
			fail(ee.getMessage());
		}
	}

	/*
	 * This test case validates if the coordinates in place command are valid.
	 * It checks if X and Y coordinates are less than 5 so that Robot move only
	 * on 5*5 grid
	 */

	@Test
	public final void testToCheckForRobotCoordinatesFor5By5Grid() {
		try {
			RobotService cwd = new RobotService(placeCommand, new Robot());
			cwd.checkForRobotCoordinatesFor5By5Grid(Arrays.asList("5", "5"));
		} catch (Exception ee) {
			fail(ee.getMessage());
		}

	}

	/*
	 * This test case validates ifTurning left only changes the direction, not
	 * changing the coordinates.
	 */

	@Test
	public final void testIfTurnLeftIsNotMovingTheRobot() {
		try {
			RobotService cwd = new RobotService(placeCommand, new Robot());
			String direction = cwd.getRobot().getFacing();

			cwd.actionTurnLeft();

			Assert.assertEquals(0, cwd.getRobot().getX());
			Assert.assertEquals(0, cwd.getRobot().getY());
			Assert.assertEquals(RobotService.configureValuesForLeftTurn
					.get(direction), cwd.getRobot().getFacing());

		} catch (Exception ee) {
			fail(ee.getMessage());
		}

	}

	/*
	 * This test case validates ifTurning Right only changes the direction, not
	 * changing the coordinates.
	 */

	@Test
	public final void testIfTurnRightIsNotMovingTheRobot() {
		try {
			RobotService cwd = new RobotService(placeCommand, new Robot());
			String direction = cwd.getRobot().getFacing();

			cwd.actionTurnRight();

			Assert.assertEquals(0, cwd.getRobot().getX());
			Assert.assertEquals(0, cwd.getRobot().getY());
			Assert.assertEquals(
					RobotService.configureValuesForRightTurn.get(direction),
					cwd.getRobot().getFacing());

		} catch (Exception ee) {
			fail(ee.getMessage());
		}
	}
}
