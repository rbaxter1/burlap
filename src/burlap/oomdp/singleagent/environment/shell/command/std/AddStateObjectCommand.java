package burlap.oomdp.singleagent.environment.shell.command.std;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentDelegation;
import burlap.oomdp.singleagent.environment.StateSettableEnvironment;
import burlap.oomdp.singleagent.environment.shell.EnvironmentShell;
import burlap.oomdp.singleagent.environment.shell.command.ShellCommand;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * @author James MacGlashan.
 */
public class AddStateObjectCommand implements ShellCommand {

	protected OptionParser parser = new OptionParser("vh*");
	protected Domain domain;

	public AddStateObjectCommand(Domain domain) {
		this.domain = domain;
	}

	@Override
	public String commandName() {
		return "addOb";
	}

	@Override
	public int call(EnvironmentShell shell, String argString, Environment env, Scanner is, PrintStream os) {

		OptionSet oset = this.parser.parse(argString.split(" "));
		List<String> args = (List<String>)oset.nonOptionArguments();
		if(oset.has("h")){
			os.println("[-v] objectClass objectName\nAdds an OO-MDP object instance of class objectClass and with name " +
					"objectName to the current state of the environment. The environment must implement StateSettableEnvironment " +
					"for this operation to work. The Java ObjectInstance implementation used will be MutableObjectInstance.\n\n" +
					"-v print the new Environment state after completion.");
			return 0;
		}

		StateSettableEnvironment senv = (StateSettableEnvironment)EnvironmentDelegation.EnvDelegationTools.getDelegateImplementing(env, StateSettableEnvironment.class);
		if(senv == null){
			os.println("Cannot add object to environment state, because the environment does not implement StateSettableEnvironment");
			return 0;
		}

		if(args.size() != 2){
			return -1;
		}

		ObjectInstance o = new MutableObjectInstance(domain.getObjectClass(args.get(0)), args.get(1));
		State s = env.getCurrentObservation();
		s.addObject(o);
		senv.setCurStateTo(s);

		if(oset.has("v")){
			os.println(senv.getCurrentObservation().getCompleteStateDescriptionWithUnsetAttributesAsNull());
		}

		return 1;
	}
}
