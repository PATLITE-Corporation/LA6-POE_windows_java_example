package jp.co.patlite.la6_poe.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Control ctr = new Control("192.168.10.1", 10000);

		// Connect to LA-POE
		int ret = ctr.SocketOpen();
		if (ret == -1) {
			return;
		}
		try {
            String commandId = "";
            if (args.length > 0) {
                commandId = args[0];
            }

            switch (commandId)
            {
                case "T":
                    {
                        // smart mode control command
                        if (args.length >= 2)
                        	ctr.PNS_SmartModeCommand((byte)Integer.parseInt(args[1]));
                        break;
                    }
                case "M":
                    {
                        // mute command
                        if (args.length >= 2)
                        	ctr.PNS_MuteCommand((byte)Integer.parseInt(args[1]));
                        break;
                    }
                case "P":
                    {
                        // stop/pulse input command
                        if (args.length >= 2)
                        	ctr.PNS_StopPulseInputCommand((byte)Integer.parseInt(args[1]));
                        break;
                    }
                case "S":
                    {
                        // operation control command
                        if (args.length >= 7)
                        {
                        	Control.PNS_RUN_CONTROL_DATA runControlData = ctr.new PNS_RUN_CONTROL_DATA();
                        	runControlData.led1Pattern = (byte)Integer.parseInt(args[1]);
                            runControlData.led2Pattern = (byte)Integer.parseInt(args[2]);
                            runControlData.led3Pattern = (byte)Integer.parseInt(args[3]);
                            runControlData.led4Pattern = (byte)Integer.parseInt(args[4]);
                            runControlData.led5Pattern = (byte)Integer.parseInt(args[5]);
                            runControlData.buzzerPattern = (byte)Integer.parseInt(args[6]);
                            ctr.PNS_RunControlCommand(runControlData);
                        }

                        break;
                    }
                case "D":
                    {
                        // detailed operation control command
                        if (args.length >= 8)
                        {
                        	Control.PNS_DETAIL_RUN_CONTROL_DATA detalRunControlData = ctr.new PNS_DETAIL_RUN_CONTROL_DATA();
                        	detalRunControlData.led1Color = (byte)Integer.parseInt(args[1]);
                        	detalRunControlData.led2Color = (byte)Integer.parseInt(args[2]);
                        	detalRunControlData.led3Color = (byte)Integer.parseInt(args[3]);
                        	detalRunControlData.led4Color = (byte)Integer.parseInt(args[4]);
                        	detalRunControlData.led5Color = (byte)Integer.parseInt(args[5]);
                        	detalRunControlData.blinkingControl = (byte)Integer.parseInt(args[6]);
                        	detalRunControlData.buzzerPattern = (byte)Integer.parseInt(args[7]);
                        	ctr.PNS_DetailRunControlCommand(detalRunControlData);
                        }

                        break;
                    }
                case "C":
                    {
                        // clear command
                    	ctr.PNS_ClearCommand();
                        break;
                    }
                case "B":
                    {
                        // reboot command
                        if (args.length >= 2)
                        	ctr.PNS_RebootCommand(args[1]);
                        break;
                    }
                case "G":
                    {
                        // get status command
            			Control.PNS_STATUS_DATA statusData = ctr.PNS_GetDataCommand();
                        if (ret == 0)
                        {
                            // Display acquired data
                            System.err.println("Response data for status acquisition command");
                            // Input1
                            System.err.println("Input1 : " + statusData.input[0]);
                            // Input2
                            System.err.println("Input2 : " + statusData.input[1]);
                            // Input3
                            System.err.println("Input3 : " + statusData.input[2]);
                            // Input4
                            System.err.println("Input4 : " + statusData.input[3]);
                            // Input5
                            System.err.println("Input5 : " + statusData.input[4]);
                            // Input6
                            System.err.println("Input6 : " + statusData.input[5]);
                            // Input7
                            System.err.println("Input7 : " + statusData.input[6]);
                            // Input8
                            System.err.println("Input8 : " + statusData.input[7]);
                            // mode
                            if (statusData.mode == Control.PNS_LED_MODE)
                            {
                                // signal light mode
                                System.err.println("signal light mode");
                                // 1st LED unit pattern
                                System.err.println("1st LED unit pattern : " + statusData.ledModeData.led1Pattern);
                                // 2nd LED unit pattern
                                System.err.println("2nd LED unit pattern : " + statusData.ledModeData.led2Pattern);
                                // 3rd LED unit pattern
                                System.err.println("3rd LED unit pattern : " + statusData.ledModeData.led3Pattern);
                                // 4th LED unit pattern
                                System.err.println("4th LED unit pattern : " + statusData.ledModeData.led4Pattern);
                                // 5th LED unit pattern
                                System.err.println("5th LED unit pattern : " + statusData.ledModeData.led5Pattern);
                                // buzzer pattern
                                System.err.println("buzzer pattern: " + statusData.ledModeData.buzzerPattern);
                            }
                            else
                            {
                                // smart mode
                                System.err.println("smart mode");
                                // group number
                                System.err.println("group number : " + statusData.smartModeData.groupNo);
                                // mute
                                System.err.println("mute : " + statusData.smartModeData.mute);
                                // STOP input
                                System.err.println("STOP input : " + statusData.smartModeData.stopInput);
                                // pattern number
                                System.err.println("pattern number : " + statusData.smartModeData.patternNo);
                            }
                        }

                        break;
                    }
                case "E":
                    {
                        // get detail status command
            			Control.PNS_DETAIL_STATUS_DATA detailStatusData = ctr.PNS_GetDetailDataCommand();
                        if (ret == 0)
                        {
                            // Display acquired data
                            System.err.println("Response data for status acquisition command");
                            // MAC address
                            System.err.println("MAC address : " + Integer.toHexString(detailStatusData.macAddress[0]) + "-"
                                                               + Integer.toHexString(detailStatusData.macAddress[1]) + "-"
                                                               + Integer.toHexString(detailStatusData.macAddress[2]) + "-"
                                                               + Integer.toHexString(detailStatusData.macAddress[3]) + "-"
                                                               + Integer.toHexString(detailStatusData.macAddress[4]) + "-"
                                                               + Integer.toHexString(detailStatusData.macAddress[5]));
                            // Input1
                            System.err.println("Input1 : " + detailStatusData.input[0]);
                            // Input2
                            System.err.println("Input2 : " + detailStatusData.input[1]);
                            // Input3
                            System.err.println("Input3 : " + detailStatusData.input[2]);
                            // Input4
                            System.err.println("Input4 : " + detailStatusData.input[3]);
                            // Input5
                            System.err.println("Input5 : " + detailStatusData.input[4]);
                            // Input6
                            System.err.println("Input6 : " + detailStatusData.input[5]);
                            // Input7
                            System.err.println("Input7 : " + detailStatusData.input[6]);
                            // Input8
                            System.err.println("Input8 : " + detailStatusData.input[7]);
                            // mode
                            if (detailStatusData.mode == Control.PNS_LED_MODE)
                            {
                                // signal light mode
                                System.err.println("signal light mode");
                                // 1st LED unit
                                System.err.println("1st LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.ledModeDetalData.ledUnit1Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.ledModeDetalData.ledUnit1Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.ledModeDetalData.ledUnit1Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.ledModeDetalData.ledUnit1Data.blue & 0xFF));
                                // 2nd LED unit
                                System.err.println("2nd LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.ledModeDetalData.ledUnit2Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.ledModeDetalData.ledUnit2Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.ledModeDetalData.ledUnit2Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.ledModeDetalData.ledUnit2Data.blue & 0xFF));
                                // 3rd LED unit
                                System.err.println("3rd LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.ledModeDetalData.ledUnit3Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.ledModeDetalData.ledUnit3Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.ledModeDetalData.ledUnit3Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.ledModeDetalData.ledUnit3Data.blue & 0xFF));
                                // 4th LED unit
                                System.err.println("4th LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.ledModeDetalData.ledUnit4Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.ledModeDetalData.ledUnit4Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.ledModeDetalData.ledUnit4Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.ledModeDetalData.ledUnit4Data.blue & 0xFF));
                                // 5th LED unit
                                System.err.println("5th LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.ledModeDetalData.ledUnit5Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.ledModeDetalData.ledUnit5Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.ledModeDetalData.ledUnit5Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.ledModeDetalData.ledUnit5Data.blue & 0xFF));
                                // buzzer pattern
                                System.err.println("buzzer pattern: " + detailStatusData.ledModeDetalData.buzzerPattern);
                            }
                            else
                            {
                                // smart mode
                                System.err.println("smart mode");
                                // group number
                                System.err.println("group number : " + detailStatusData.smartModeDetalData.smartModeData.groupNo);
                                // mute
                                System.err.println("mute : " + detailStatusData.smartModeDetalData.smartModeData.mute);
                                // STOP input
                                System.err.println("STOP input : " + detailStatusData.smartModeDetalData.smartModeData.stopInput);
                                // pattern number
                                System.err.println("pattern number : " + detailStatusData.smartModeDetalData.smartModeData.patternNo);
                                // last pattern
                                System.err.println("last pattern : " + detailStatusData.smartModeDetalData.smartModeData.lastPattern);
                                // 1st LED unit
                                System.err.println("1st LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.smartModeDetalData.ledUnit1Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.smartModeDetalData.ledUnit1Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.smartModeDetalData.ledUnit1Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.smartModeDetalData.ledUnit1Data.blue & 0xFF));
                                // 2nd LED unit
                                System.err.println("2nd LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.smartModeDetalData.ledUnit2Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.smartModeDetalData.ledUnit2Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.smartModeDetalData.ledUnit2Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.smartModeDetalData.ledUnit2Data.blue & 0xFF));
                                // 3rd LED unit
                                System.err.println("3rd LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.smartModeDetalData.ledUnit3Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.smartModeDetalData.ledUnit3Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.smartModeDetalData.ledUnit3Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.smartModeDetalData.ledUnit3Data.blue & 0xFF));
                                // 4th LED unit
                                System.err.println("4th LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.smartModeDetalData.ledUnit4Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.smartModeDetalData.ledUnit4Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.smartModeDetalData.ledUnit4Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.smartModeDetalData.ledUnit4Data.blue & 0xFF));
                                // 5th LED unit
                                System.err.println("5th LED unit");
                                // pattern
                                System.err.println("pattern : " + detailStatusData.smartModeDetalData.ledUnit5Data.ledPattern);
                                // R
                                System.err.println("R : " + (detailStatusData.smartModeDetalData.ledUnit5Data.red & 0xFF));
                                // G
                                System.err.println("G : " + (detailStatusData.smartModeDetalData.ledUnit5Data.green & 0xFF));
                                // B
                                System.err.println("B : " + (detailStatusData.smartModeDetalData.ledUnit5Data.blue & 0xFF));
                                // buzzer pattern
                                System.err.println("buzzer pattern: " + detailStatusData.smartModeDetalData.buzzerPattern);
                            }
                        }

                        break;
                    }
                case "W":
                    {
                        // write command
                        if (args.length >= 2)
                        	ctr.PHN_WriteCommand((byte)Integer.parseInt(args[1]));
                        break;
                    }
                case "R":
                    {
                        // read command
                        byte[] runData = ctr.PHN_ReadCommand();
                        if (ret == 0)
                        {
                            // Display acquired data
                            System.err.println("Response data for read command");
                            // LED unit flashing
                            System.err.println("LED unit flashing");
                            // 1st LED unit
                            System.err.println("1st LED unit : " + ((runData[0] & 0x20) != 0 ? 1 : 0));
                            // 2nd LED unit
                            System.err.println("2nd LED unit : " + ((runData[0] & 0x40) != 0 ? 1 : 0));
                            // 3rd LED unit
                            System.err.println("3rd LED unit : " + ((runData[0] & 0x80) != 0 ? 1 : 0));
                            // buzzer pattern
                            System.err.println("buzzer pattern");
                            // pattern1
                            System.err.println("pattern1 : " + ((runData[0] & 0x8) != 0 ? 1 : 0));
                            // pattern2
                            System.err.println("pattern2 : " + ((runData[0] & 0x10) != 0 ? 1 : 0));
                            // LED unit lighting
                            System.err.println("LED unit lighting");
                            // 1st LED unit
                            System.err.println("1st LED unit : " + ((runData[0] & 0x1) != 0 ? 1 : 0));
                            // 2nd LED unit
                            System.err.println("2nd LED unit : " + ((runData[0] & 0x2) != 0 ? 1 : 0));
                            // 3rd LED unit
                            System.err.println("3rd LED unit : " + ((runData[0] & 0x4) != 0 ? 1 : 0));
                        }

                        break;
                    }
            }
		} finally {
            // Close the socket
			ctr.SocketClose();
		}
	}

}

class Control {
	/** product category */
	private static short PNS_PRODUCT_ID = 0x4142;

	// PNS command identifier
	/** smart mode control command */
	private static byte PNS_SMART_MODE_COMMAND = 0x54;
	/** mute command */
	private static byte PNS_MUTE_COMMAND = 0x4D;
	/** stop/pulse input command */
	private static byte PNS_STOP_PULSE_INPUT_COMMAND = 0x50;
	/** operation control command */
	private static byte PNS_RUN_CONTROL_COMMAND = 0x53;
	/** detailed operation control command */
	private static byte PNS_DETAIL_RUN_CONTROL_COMMAND = 0x44;
	/** clear command */
	private static byte PNS_CLEAR_COMMAND = 0x43;
	/** reboot command */
	private static byte PNS_REBOOT_COMMAND = 0x42;
	/** get status command */
	private static byte PNS_GET_DATA_COMMAND = 0x47;
	/** get detail status command */
	private static byte PNS_GET_DETAIL_DATA_COMMAND = 0x45;

	// response data for PNS command
	/** normal response */
	private static byte PNS_ACK = 0x06;
	/** abnormal response */
	private static byte PNS_NAK = 0x15;

	// mode
	/** signal light mode */
	public static byte PNS_LED_MODE = 0x00;
	/** smart mode */
	public static byte PNS_SMART_MODE = 0x01;

	// LED unit for motion control command
	/** light off */
	public static byte PNS_RUN_CONTROL_LED_OFF = 0x00;
	/** light on */
	public static byte PNS_RUN_CONTROL_LED_ON = 0x01;
	/** flashing */
	public static byte PNS_RUN_CONTROL_LED_BLINKING = 0x02;
	/** no change */
	public static byte PNS_RUN_CONTROL_LED_NO_CHANGE = 0x09;

	// buzzer for motion control command
	/** stop */
	public static byte PNS_RUN_CONTROL_BUZZER_STOP = 0x00;
	/** pattern 1 */
	public static byte PNS_RUN_CONTROL_BUZZER_PATTERN1 = 0x01;
	/** pattern 2 */
	public static byte PNS_RUN_CONTROL_BUZZER_PATTERN2 = 0x02;
	/** buzzer tone for simultaneous buzzer input */
	public static byte PNS_RUN_CONTROL_BUZZER_TONE = 0x03;
	/** no change */
	public static byte PNS_RUN_CONTROL_BUZZER_NO_CHANGE = 0x09;

	// LED unit for detailed operation control command
	/** light off */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_OFF = 0x00;
	/** red */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_RED = 0x01;
	/** yellow */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_YELLOW = 0x02;
	/** lemon */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_LEMON = 0x03;
	/** green */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_GREEN = 0x04;
	/** sky blue */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_SKY_BLUE = 0x05;
	/** blue */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_BLUE = 0x06;
	/** purple */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_PURPLE = 0x07;
	/** peach */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_PEACH = 0x08;
	/** white */
	public static byte PNS_DETAIL_RUN_CONTROL_LED_COLOR_WHITE = 0x09;

	// blinking action for detailed action control command
	/** blinking off */
	public static byte PNS_DETAIL_RUN_CONTROL_BLINKING_OFF = 0x00;
	/** blinking ON */
	public static byte PNS_DETAIL_RUN_CONTROL_BLINKING_ON = 0x01;

	// buzzer for detailed action control command
	/** stop */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_STOP = 0x00;
	/** pattern 1 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN1 = 0x01;
	/** pattern 2 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN2 = 0x02;
	/** pattern 3 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN3 = 0x03;
	/** pattern 4 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN4 = 0x04;
	/** pattern 5 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN5 = 0x05;
	/** pattern 6 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN6 = 0x06;
	/** pattern 7 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN7 = 0x07;
	/** pattern 8 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN8 = 0x08;
	/** pattern 9 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN9 = 0x09;
	/** pattern 10 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN10 = 0x0A;
	/** pattern 11 */
	public static byte PNS_DETAIL_RUN_CONTROL_BUZZER_PATTERN11 = 0x0B;

	/**
	 * operation control data structure
	 */
	public class PNS_RUN_CONTROL_DATA {
		/** 1st LED unit pattern */
		public byte led1Pattern = 0;

		/** 2nd LED unit pattern */
		public byte led2Pattern = 0;

		/** 3rd LED unit pattern */
		public byte led3Pattern = 0;

		/** 4th LED unit pattern */
		public byte led4Pattern = 0;

		/** 5th LED unit pattern */
		public byte led5Pattern = 0;

		/** buzzer pattern 1 to 3 */
		public byte buzzerPattern = 0;
	}

	/**
	 * detail operation control data structure
	 */
	public class PNS_DETAIL_RUN_CONTROL_DATA {
		/** 1st color of LED unit */
		public byte led1Color = 0;

		/** 2nd color of LED unit */
		public byte led2Color = 0;

		/** 3rd color of LED unit */
		public byte led3Color = 0;

		/** 4th color of LED unit */
		public byte led4Color = 0;

		/** 5th color of LED unit */
		public byte led5Color = 0;

		/** blinking action */
		public byte blinkingControl = 0;

		/** buzzer pattern 1 to 11 */
		public byte buzzerPattern = 0;
	}

	/**
	 * status data of operation control
	 */
	public class PNS_STATUS_DATA {
		/** input 1 to 8 */
		public byte[] input = new byte[8];

		/** mode */
		public byte mode = 0;

		/** status data when running signal light mode */
		public PNS_LED_MODE_DATA ledModeData = null;

		/** status data during smart mode execution */
		public PNS_SMART_MODE_DATA smartModeData = null;
	}

	/**
	 * status data when running in signal light mode
	 */
	public class PNS_LED_MODE_DATA {
		/** 1st LED unit pattern */
		public byte led1Pattern = 0;

		/** 2nd LED unit pattern */
		public byte led2Pattern = 0;

		/** 3rd LED unit pattern */
		public byte led3Pattern = 0;

		/** 4th LED unit pattern */
		public byte led4Pattern = 0;

		/** 5th LED unit pattern */
		public byte led5Pattern = 0;

		/** buzzer patterns 1 through 11 */
		public byte buzzerPattern = 0;
	}

	/**
	 * state data when running smart mode
	 */
	public class PNS_SMART_MODE_DATA {
		/** group number */
		public byte groupNo = 0;

		/** mute */
		public byte mute = 0;

		/** STOP input */
		public byte stopInput = 0;

		/** pattern number */
		public byte patternNo = 0;
	}

	/**
	 * status data of detailed operation control
	 */
	public class PNS_DETAIL_STATUS_DATA {
		/** MAC address */
		public byte[] macAddress = new byte[6];

		/** Input 1 to 8*/
		public byte[] input = new byte[8];

		/** mode */
		public byte mode = 0;

		/** detailed status data when running signal light mode */
		public PNS_LED_MODE_DETAIL_DATA ledModeDetalData = null;

		/** detailed state data when running in smart mode */
		public PNS_SMART_MODE_DETAIL_DATA smartModeDetalData = null;
	}

	/**
	 * detailed state data when running in signal light mode
	 */
	public class PNS_LED_MODE_DETAIL_DATA {
		/** 1st stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit1Data = null;

		/** 2nd stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit2Data = null;

		/** 3rd stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit3Data = null;

		/** 4th stage of LED units */
		public PNS_LED_UNIT_DATA ledUnit4Data = null;

		/** 5th stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit5Data = null;

		/** buzzer pattern 1 to 11 */
		public byte buzzerPattern = 0;
	}

	/**
	 * LED unit data
	 */
	public class PNS_LED_UNIT_DATA {
		/** status */
		public byte ledPattern = 0;

		/** R */
		public byte red = 0;

		/** G */
		public byte green = 0;

		/** B */
		public byte blue = 0;
	}

	/**
	 * detail state data for smart mode execution
	 */
	public class PNS_SMART_MODE_DETAIL_DATA {
		/** smart mode state */
		public PNS_SMART_MODE_DETAIL_STATE_DATA smartModeData = null;

		/** 1st stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit1Data = null;

		/** 2nd stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit2Data = null;

		/** 3rd stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit3Data = null;

		/** 4th stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit4Data = null;

		/** 5th stage of LED unit */
		public PNS_LED_UNIT_DATA ledUnit5Data = null;

		/** buzzer pattern 1 to 11 */
		public byte buzzerPattern = 0;
	}

	/**
	 * smart mode status data
	 */
	public class PNS_SMART_MODE_DETAIL_STATE_DATA {
		/** group number */
		public byte groupNo = 0;

		/** mute */
		public byte mute = 0;

		/** STOP input */
		public byte stopInput = 0;

		/** pattern number */
		public byte patternNo = 0;

		/** last pattern */
		public byte lastPattern = 0;
	}

	// PHN command identifier
	/** write command */
	private static byte PHN_WRITE_COMMAND = 0x57;
	/** read command */
	private static byte PHN_READ_COMMAND = 0x52;

	// response data for PNS command
	/** normal response */
	private static byte[] PHN_ACK = { 0x41, 0x43, 0x4B };
	/** abnormal response */
	private static byte[] PHN_NAK = { 0x4E, 0x41, 0x4B };

	// action data of PHN command
	/** 1st LED unit blinking */
	public static byte PHN_LED_UNIT1_BLINKING = 0x20;
	/** 2nd LED unit blinking */
	public static byte PHN_LED_UNIT2_BLINKING = 0x40;
	/** 3rd LED unit blinking */
	public static byte PHN_LED_UNIT3_BLINKING = (byte) 0x80;
	/** buzzer pattern 1 */
	public static byte PHN_BUZZER_PATTERN1 = 0x08;
	/** buzzer pattern 2 */
	public static byte PHN_BUZZER_PATTERN2 = 0x10;
	/** 1st LED unit lighting */
	public static byte PHN_LED_UNIT1_LIGHTING = 0x01;
	/** 2nd LED unit lighting */
	public static byte PHN_LED_UNIT2_LIGHTING = 0x02;
	/** 3rd LED unit lighting */
	public static byte PHN_LED_UNIT3_LIGHTING = 0x04;

	// local variable
	/** IP address */
	private String ip;
	/** port number */
	private int port;
	/** Socket */
	private Socket sock;
	/** Transmit stream */
	private OutputStream out;
	/** Receive Stream */
	private InputStream in;

	/**
	 * constructor
	 *
	 * @param ip   IP address
	 * @param port port number
	 */
	Control(final String ip, final int port) {
		this.ip = ip;
		this.port = port;
		this.sock = null;
		this.out = null;
		this.in = null;
	}

	/**
	 * Connect to LA-POE
	 *
	 * @return success: 0, failure: non-zero
	 */
	public int SocketOpen() {
		try {
			// Create a socket
			this.sock = new Socket(this.ip, this.port);

			// Obtaining the transmitted and received streams
			this.out = this.sock.getOutputStream();
			this.in = this.sock.getInputStream();

		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}

		return 0;
	}

	/**
	 * Close the socket.
	 */
	public void SocketClose() {
		try {
			if (this.in != null) {
				this.in.close();
				this.in = null;
			}

			if (this.out != null) {
				this.out.close();
				this.out = null;
			}

			if (this.sock != null) {
				this.sock.close();
				this.sock = null;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Send command
	 *
	 * @param sendData send data
	 * @return received data
	 */
	private byte[] SendCommand(final byte[] sendData) {
		try {
			if (this.sock == null) {
				System.err.println("socket is not");
				return null;
			}

			// Send
			this.out.write(sendData);

			// Receive response data
			byte[] recvData = new byte[1024];
			int size = this.in.read(recvData);
			// Truncate the incoming data to the size you read in.
			recvData = Arrays.copyOf(recvData, size);

			return recvData;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Send smart mode control command for PNS command
	 * Smart mode can be executed for the number specified in the data area
	 *
	 * @param groupNo Group number to execute smart mode (0x01(Group No.1) to 0x1F(Group No.31))
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_SmartModeCommand(final int groupNo) {
		ByteBuffer sendData = ByteBuffer.allocate(7);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (T)
		sendData.put(PNS_SMART_MODE_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 1);

		// Data area
		sendData.put((byte) groupNo);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send mute command for PNS command<br>
	 * Can control the buzzer ON/OFF while Smart Mode is running
	 *
	 * @param mute Buzzer ON/OFF (ON: 1, OFF: 0)
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_MuteCommand(final int mute) {
		ByteBuffer sendData = ByteBuffer.allocate(7);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (M)
		sendData.put(PNS_MUTE_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 1);

		// Data area
		sendData.put((byte) mute);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send stop/pulse input command for PNS command<br>
	 * Transmit during time trigger mode operation to control stop/resume of pattern (STOP input)<br>
	 * Sending this command during pulse trigger mode operation enables pattern transition (trigger input)
	 *
	 * @param input STOP input/trigger input (STOP input ON/trigger input: 1, STOP input: 0)
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_StopPulseInputCommand(final int input) {
		ByteBuffer sendData = ByteBuffer.allocate(7);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (P)
		sendData.put(PNS_STOP_PULSE_INPUT_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 1);

		// Data area
		sendData.put((byte) input);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send operation control command for PNS command<br>
	 * Each stage of the LED unit and the buzzer (1 to 3) can be controlled by the pattern specified in the data area<br>
	 * Operates with the color and buzzer set in the signal light mode
	 *
	 * @param runControlData Pattern of the 1st to 5th stage of the LED unit and buzzer (1 to 3)<br>
	 *                       Pattern of LED unit (off: 0, on: 1, blinking: 2, no change: 9)<br>
	 *                       Pattern of buzzer (stop: 0, pattern 1: 1, pattern 2: 2, buzzer tone when input simultaneously with buzzer: 3, no change: 9)
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_RunControlCommand(final PNS_RUN_CONTROL_DATA runControlData) {
		ByteBuffer sendData = ByteBuffer.allocate(12);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (S)
		sendData.put(PNS_RUN_CONTROL_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size、Data area
		byte[] data = { runControlData.led1Pattern, // 1st LED unit pattern
				runControlData.led2Pattern, // 2nd LED unit pattern
				runControlData.led3Pattern, // 3rd LED unit pattern
				runControlData.led4Pattern, // 4th LED unit pattern
				runControlData.led5Pattern, // 5th LED unit pattern
				runControlData.buzzerPattern, // Buzzer pattern 1 to 3
		};
		sendData.putShort((short) data.length);
		sendData.put(data);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send detailed operation control command for PNS command<br>
	 * The color and operation pattern of each stage of the LED unit and the buzzer pattern (1 to 11) can be specified and controlled in the data area
	 *
	 * @param detailRunControlData Pattern of the 1st to 5th stage of the LED unit, blinking operation and buzzer (1 to 3)<br>
	 *                            Pattern of LED unit (off: 0, red: 1, yellow: 2, lemon: 3, green: 4, sky blue: 5, blue: 6, purple: 7, peach: 8, white: 9)<br>
	 *                            Flashing action (Flashing OFF: 0, Flashing ON: 1)<br>
	 *                            Buzzer pattern (Stop: 0, Pattern 1: 1, Pattern 2: 2, Pattern 3: 3, Pattern 4: 4, Pattern 5: 5, Pattern 6: 6, Pattern 7: 7, Pattern 8: 8, Pattern 9: 9, Pattern 10: 10, Pattern 11: 11)
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_DetailRunControlCommand(final PNS_DETAIL_RUN_CONTROL_DATA detailRunControlData) {
		ByteBuffer sendData = ByteBuffer.allocate(13);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (D)
		sendData.put(PNS_DETAIL_RUN_CONTROL_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size、Data area
		byte[] data = { detailRunControlData.led1Color, // 1st color of LED unit
				detailRunControlData.led2Color, // 2nd color of LED unit
				detailRunControlData.led3Color, // 3rd color of LED unit
				detailRunControlData.led4Color, // 4th color of LED unit
				detailRunControlData.led5Color, // 5th color of LED unit
				detailRunControlData.blinkingControl, // blinking operation
				detailRunControlData.buzzerPattern, // buzzer pattern 1 to 11
		};
		sendData.putShort((short) data.length);
		sendData.put(data);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send clear command for PNS command<br>
	 * Turn off the LED unit and stop the buzzer
	 *
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_ClearCommand() {
		ByteBuffer sendData = ByteBuffer.allocate(6);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (C)
		sendData.put(PNS_CLEAR_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 0);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send restart command for PNS command<br>
	 * LA6-POE can be restarted
	 *
	 * @param password Password set in the password setting of Web Configuration
	 * @return success: 0, failure: non-zero
	 */
	public int PNS_RebootCommand(final String password) {
		byte[] data = password.getBytes();

		ByteBuffer sendData = ByteBuffer.allocate(6 + data.length);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (B)
		sendData.put(PNS_REBOOT_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) data.length);

		// Data area
		sendData.put(data);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send status acquisition command for PNS command<br>
	 * Signal line/contact input status and LED unit and buzzer status can be acquired
	 *
	 * @return Received data of status acquisition command (status of signal line/contact input and status of LED unit and buzzer)
	 */
	public PNS_STATUS_DATA PNS_GetDataCommand() {
		ByteBuffer sendData = ByteBuffer.allocate(6);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (G)
		sendData.put(PNS_GET_DATA_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 0);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return null;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return null;
		}

		PNS_STATUS_DATA statusData = new PNS_STATUS_DATA();

		// Input 1 to 8
		System.arraycopy(recvData, 0, statusData.input, 0, statusData.input.length);

		// Mode
		statusData.mode = recvData[8];

		// Check the mode
		if (statusData.mode == PNS_LED_MODE) {
			// signal light mode
			statusData.ledModeData = new PNS_LED_MODE_DATA();
			statusData.ledModeData.led1Pattern = recvData[9]; // 1st LED unit pattern
			statusData.ledModeData.led2Pattern = recvData[10]; // 2nd LED unit pattern
			statusData.ledModeData.led3Pattern = recvData[11]; // 3rd LED unit pattern
			statusData.ledModeData.led4Pattern = recvData[12]; // 4th LED unit pattern
			statusData.ledModeData.led5Pattern = recvData[13]; // 5th LED unit pattern
			statusData.ledModeData.buzzerPattern = recvData[14]; // buzzer pattern 1 to 11
		} else {
			// smart mode
			statusData.smartModeData = new PNS_SMART_MODE_DATA();
			statusData.smartModeData.groupNo = recvData[9]; // group number
			statusData.smartModeData.mute = recvData[10]; // mute
			statusData.smartModeData.stopInput = recvData[11]; // STOP input
			statusData.smartModeData.patternNo = recvData[12]; // pattern number
		}

		return statusData;
	}

	/**
	 * Send command to get detailed status of PNS command<br>
	 * Signal line/contact input status, LED unit and buzzer status, and color information for each stage can be acquired
	 *
	 * @return Received data of detail status acquisition command (status of signal line/contact input, status of LED unit and buzzer, and color information of each stage)
	 */
	public PNS_DETAIL_STATUS_DATA PNS_GetDetailDataCommand() {
		ByteBuffer sendData = ByteBuffer.allocate(6);

		// Product Category (AB)
		sendData.putShort(PNS_PRODUCT_ID);

		// Command identifier (E)
		sendData.put(PNS_GET_DETAIL_DATA_COMMAND);

		// Empty
		sendData.put((byte) 0x00);

		// Data size
		sendData.putShort((short) 0);

		// Send PNS command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return null;
		}

		// check the response data
		if (recvData[0] == PNS_NAK) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return null;
		}

		PNS_DETAIL_STATUS_DATA detailStatusData = new PNS_DETAIL_STATUS_DATA();

		// MAC Address
		System.arraycopy(recvData, 0, detailStatusData.macAddress, 0, detailStatusData.macAddress.length);

		// Input 1 to 8
		System.arraycopy(recvData, 6, detailStatusData.input, 0, detailStatusData.input.length);

		// Mode
		detailStatusData.mode = recvData[14];

		// Check the mode
		if (detailStatusData.mode == PNS_LED_MODE) {
			// signal light mode
			detailStatusData.ledModeDetalData = new PNS_LED_MODE_DETAIL_DATA();

			// 1st stage of LED unit
			detailStatusData.ledModeDetalData.ledUnit1Data = new PNS_LED_UNIT_DATA();
			detailStatusData.ledModeDetalData.ledUnit1Data.ledPattern = recvData[19]; // state
			detailStatusData.ledModeDetalData.ledUnit1Data.red = recvData[20]; // R
			detailStatusData.ledModeDetalData.ledUnit1Data.green = recvData[21]; // G
			detailStatusData.ledModeDetalData.ledUnit1Data.blue = recvData[22]; // B

			// 2nd stage of LED unit
			detailStatusData.ledModeDetalData.ledUnit2Data = new PNS_LED_UNIT_DATA();
			detailStatusData.ledModeDetalData.ledUnit2Data.ledPattern = recvData[23]; // state
			detailStatusData.ledModeDetalData.ledUnit2Data.red = recvData[24]; // R
			detailStatusData.ledModeDetalData.ledUnit2Data.green = recvData[25]; // G
			detailStatusData.ledModeDetalData.ledUnit2Data.blue = recvData[26]; // B

			// 3rd stage of LED unit
			detailStatusData.ledModeDetalData.ledUnit3Data = new PNS_LED_UNIT_DATA();
			detailStatusData.ledModeDetalData.ledUnit3Data.ledPattern = recvData[27]; // state
			detailStatusData.ledModeDetalData.ledUnit3Data.red = recvData[28]; // R
			detailStatusData.ledModeDetalData.ledUnit3Data.green = recvData[29]; // G
			detailStatusData.ledModeDetalData.ledUnit3Data.blue = recvData[30]; // B

			// 4th stage of LED unit
			detailStatusData.ledModeDetalData.ledUnit4Data = new PNS_LED_UNIT_DATA();
			detailStatusData.ledModeDetalData.ledUnit4Data.ledPattern = recvData[31]; // state
			detailStatusData.ledModeDetalData.ledUnit4Data.red = recvData[32]; // R
			detailStatusData.ledModeDetalData.ledUnit4Data.green = recvData[33]; // G
			detailStatusData.ledModeDetalData.ledUnit4Data.blue = recvData[34]; // B

			// 5th stage of LED unit
			detailStatusData.ledModeDetalData.ledUnit5Data = new PNS_LED_UNIT_DATA();
			detailStatusData.ledModeDetalData.ledUnit5Data.ledPattern = recvData[35]; // state
			detailStatusData.ledModeDetalData.ledUnit5Data.red = recvData[36]; // R
			detailStatusData.ledModeDetalData.ledUnit5Data.green = recvData[37]; // G
			detailStatusData.ledModeDetalData.ledUnit5Data.blue = recvData[38]; // B

			// buzzer patterns 1-11
			detailStatusData.ledModeDetalData.buzzerPattern = recvData[39];
		} else {
			// smart mode
			detailStatusData.smartModeDetalData = new PNS_SMART_MODE_DETAIL_DATA();

			// smart mode status
			detailStatusData.smartModeDetalData.smartModeData = new PNS_SMART_MODE_DETAIL_STATE_DATA();
			detailStatusData.smartModeDetalData.smartModeData.groupNo = recvData[19];
			detailStatusData.smartModeDetalData.smartModeData.mute = recvData[20];
			detailStatusData.smartModeDetalData.smartModeData.stopInput = recvData[21];
			detailStatusData.smartModeDetalData.smartModeData.patternNo = recvData[22];
			detailStatusData.smartModeDetalData.smartModeData.lastPattern = recvData[23];

			// 1st stage of LED unit
			detailStatusData.smartModeDetalData.ledUnit1Data = new PNS_LED_UNIT_DATA();
			detailStatusData.smartModeDetalData.ledUnit1Data.ledPattern = recvData[24]; // state
			detailStatusData.smartModeDetalData.ledUnit1Data.red = recvData[25]; // R
			detailStatusData.smartModeDetalData.ledUnit1Data.green = recvData[26]; // G
			detailStatusData.smartModeDetalData.ledUnit1Data.blue = recvData[27]; // B

			// 2nd stage of LED unit
			detailStatusData.smartModeDetalData.ledUnit2Data = new PNS_LED_UNIT_DATA();
			detailStatusData.smartModeDetalData.ledUnit2Data.ledPattern = recvData[28]; // state
			detailStatusData.smartModeDetalData.ledUnit2Data.red = recvData[29]; // R
			detailStatusData.smartModeDetalData.ledUnit2Data.green = recvData[30]; // G
			detailStatusData.smartModeDetalData.ledUnit2Data.blue = recvData[31]; // B

			// 3rd stage of LED unit
			detailStatusData.smartModeDetalData.ledUnit3Data = new PNS_LED_UNIT_DATA();
			detailStatusData.smartModeDetalData.ledUnit3Data.ledPattern = recvData[32]; // state
			detailStatusData.smartModeDetalData.ledUnit3Data.red = recvData[33]; // R
			detailStatusData.smartModeDetalData.ledUnit3Data.green = recvData[34]; // G
			detailStatusData.smartModeDetalData.ledUnit3Data.blue = recvData[35]; // B

			// 4th stage of LED unit
			detailStatusData.smartModeDetalData.ledUnit4Data = new PNS_LED_UNIT_DATA();
			detailStatusData.smartModeDetalData.ledUnit4Data.ledPattern = recvData[36]; // state
			detailStatusData.smartModeDetalData.ledUnit4Data.red = recvData[37]; // R
			detailStatusData.smartModeDetalData.ledUnit4Data.green = recvData[38]; // G
			detailStatusData.smartModeDetalData.ledUnit4Data.blue = recvData[39]; // B

			// 5th stage of LED unit
			detailStatusData.smartModeDetalData.ledUnit5Data = new PNS_LED_UNIT_DATA();
			detailStatusData.smartModeDetalData.ledUnit5Data.ledPattern = recvData[40]; // state
			detailStatusData.smartModeDetalData.ledUnit5Data.red = recvData[41]; // R
			detailStatusData.smartModeDetalData.ledUnit5Data.green = recvData[42]; // G
			detailStatusData.smartModeDetalData.ledUnit5Data.blue = recvData[43]; // B

			// buzzer patterns 1-11
			detailStatusData.smartModeDetalData.buzzerPattern = recvData[44];
		}

		return detailStatusData;
	}

	/**
	 * Send PHN command write command<br>
	 * Can control the lighting and blinking of LED units 1 to 3 stages, and buzzer patterns 1 and 2
	 *
	 * @param runData Operation data for lighting and blinking of LED unit 1 to 3 stages, and buzzer pattern 1 and 2<br>
	 *                bit7: 3rd LED unit blinking (OFF: 0, ON: 1)<br>
	 *                bit6: 2nd LED unit blinking (OFF: 0, ON: 1)<br>
	 *                bit5: 1st LED unit blinking (OFF: 0, ON: 1)<br>
	 *                bit4: Buzzer pattern 2 (OFF: 0, ON: 1)<br>
	 *                bit3: Buzzer pattern 1 (OFF: 0, ON: 1)<br>
	 *                bit2: 3rd LED unit lighting (OFF: 0, ON: 1)<br>
	 *                bit1: 2nd LED unit lighting (OFF: 0, ON: 1)<br>
	 *                bit0: 1st LED unit lighting (OFF: 0, ON: 1)
	 * @return success: 0, failure: non-zero
	 */
	public int PHN_WriteCommand(final byte runData) {
		ByteBuffer sendData = ByteBuffer.allocate(2);

		// Command identifier (W)
		sendData.put(PHN_WRITE_COMMAND);

		// Operation data
		sendData.put(runData);

		// send PHN command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return -1;
		}

		// check the response data
		if (Arrays.equals(recvData, PHN_NAK)) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return -1;
		}

		return 0;
	}

	/**
	 * Send command to read PHN command<br>
	 * Get information about LED unit 1 to 3 stage lighting and blinking, and buzzer pattern 1 and 2
	 *
	 * @return Received data of read command (operation data of LED unit 1 to 3 stages lighting and blinking, buzzer pattern 1,2)
	 */
	public byte[] PHN_ReadCommand() {
		ByteBuffer sendData = ByteBuffer.allocate(1);

		// Command identifier (R)
		sendData.put(PHN_READ_COMMAND);

		// send PHN command
		byte[] recvData = this.SendCommand(sendData.array());
		if (recvData == null) {
			System.err.println("failed to send data");
			return null;
		}

		// check the response data
		if (recvData[0] != PHN_READ_COMMAND) {
			// receive abnormal response
			System.err.println("negative acknowledge");
			return null;
		}

		return new byte[] { recvData[1] };
	}
}
