package com.robot.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.robot.model.Robot;

public class RobotService {

	private Robot robot;

	public static HashMap<String, String> configureValuesForLeftTurn = setConfigureValuesForLeftTurn();

	public static HashMap<String, String> configureValuesForRightTurn = setConfigureValuesForRightTurn();

	public static HashMap<String, String> setConfigureValuesForLeftTurn() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EAST", "NORTH");
		map.put("NORTH", "WEST");
		map.put("WEST", "SOUTH");
		map.put("SOUTH", "EAST");
		return map;
	}

	public static HashMap<String, String> setConfigureValuesForRightTurn() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EAST", "SOUTH");
		map.put("SOUTH", "WEST");
		map.put("WEST", "NORTH");
		map.put("NORTH", "EAST");
		return map;
	}

	/*
	 * This constructor makes it sure that the first command is only PLACE
	 * command
	 */
	public RobotService(String command, Robot robot) throws Exception {
		try {
			this.robot = robot;
			validatePlaceCommand(command);
		} catch (Exception ee) {
			throw new Exception("Exception");
		}
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public void validatePlaceCommand(String placeCommand) throws Exception {
		try{
		if (isValidPlaceCommand(placeCommand)) {
			this.robot.setCommand("PLACE");
			setCoordinatesForPlaceCommand(placeCommand);
		} else
			throw new Exception("Exception");
		}
		catch(Exception ee)
		{
			
		}
	}

	public Boolean isValidPlaceCommand(String placeCommand) {
		// Parsing PLACE Command which is supposed to look like
		// "PLACE 0,0,NORTH".
		if (placeCommand.trim().substring(0, 5).equals("PLACE")) {
			return true;
		}
		return false;
	}

	public void setCoordinatesForPlaceCommand(String placeCommand)
			throws Exception {
		// trim() is being used to ignore space
		List<String> coordinates = Arrays.asList(placeCommand.trim()
				.substring(5).split(",")); // This gives 3 coordinates values of
											// X,Y,F
		if (coordinates.size() == 3) {

			checkForRobotCoordinatesFor5By5Grid(coordinates);

			String direction = coordinates.get(2).trim().toUpperCase();
			if (direction.equals("EAST") || direction.equals("WEST")
					|| direction.equals("NORTH") || direction.equals("SOUTH")) {
				this.robot.setFacing(coordinates.get(2).trim());
			} 
		} else
			throw new Exception("Exception");
	}

	public void checkForRobotCoordinatesFor5By5Grid(List<String> coordinates)
			throws Exception {
		int X = Integer.parseInt(coordinates.get(0).toString().trim());

		int Y = Integer.parseInt(coordinates.get(1).toString().trim());

		if (X > 5 || Y > 5) // NOt letting the Robot fall
		{
			throw new Exception("Exception");
		} else {
			this.robot.setX(X);
			this.robot.setY(Y);
		}

	}

	public void action(List<String> listOfAction) {
		try {
			for (String s : listOfAction) {

				switch (s.trim().toUpperCase()) {

				case "LEFT":
					actionTurnLeft();
					break;

				case "RIGHT":
					actionTurnRight();
					break;

				case "MOVE":
					actionMove();
					break;

				case "REPORT":
					actionReport();
					break;
				default:
					if(s.substring(0, 5).equals("PLACE"))
					{
						validatePlaceCommand(s);
						break;
					}
					else break;
				}
			}
		} catch (Exception ee) {
			System.err.println("An exception was thrown");
		}

	}

	public void actionTurnLeft() {
		this.robot.setFacing(configureValuesForLeftTurn.get(this.robot
				.getFacing().trim()));
	}

	public void actionTurnRight() {
		this.robot.setFacing(configureValuesForRightTurn.get(this.robot
				.getFacing()));
	}

	public void actionMove() {
		String direction = this.robot.getFacing().toUpperCase();
		if (direction.equals("WEST")) {
			if (this.robot.getX() == 0) {
				robotFallingWarning();
			} else {
				this.robot.setX(this.robot.getX() - 1);
			}

		}
		if (direction.equals("EAST")) {
			if (this.robot.getX() == 5) {
				robotFallingWarning();
			} else {
				this.robot.setX(this.robot.getX() + 1);
			}

		}

		if (direction.equals("SOUTH")) {
			if (this.robot.getY() == 0) {
				robotFallingWarning();
			} else {
				;
				this.robot.setY(this.robot.getY() - 1);
			}
		}

		if (direction.equals("NORTH")) {
			if (this.robot.getY() == 5) {
				robotFallingWarning();
			} else {
				this.robot.setY(this.robot.getY() + 1);
			}
		}

	}

	private void robotFallingWarning() {
		System.out.println("This move can cause Robot to fall.");
	}

	public void actionReport() {
		System.out.println(this.robot.getX() + "," + this.robot.getY() + ","
				+ this.robot.getFacing());
	}

}
