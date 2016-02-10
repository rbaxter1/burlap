package burlap.oomdp.singleagent.environment.shell.command.std;

import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.shell.EnvironmentShell;
import burlap.oomdp.singleagent.environment.shell.command.ShellCommand;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author James MacGlashan.
 */
public class IsTerminalCommand implements ShellCommand{

	protected OptionParser parser = new OptionParser("h*");

	@Override
	public String commandName() {
		return "term";
	}

	@Override
	public int call(EnvironmentShell shell, String argString, Environment env, Scanner is, PrintStream os) {

		OptionSet oset = this.parser.parse(argString.split(" "));
		if(oset.has("h")){
			os.println("Prints whether the environment is in a terminal state or not (true if so, false otherwise)");
			return 0;
		}

		os.println("" + env.isInTerminalState());

		return 0;
	}
}
