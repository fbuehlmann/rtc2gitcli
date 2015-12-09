/**
 *
 */

package to.rtc.cli.migrate.command;

import java.util.ArrayList;
import java.util.List;

import com.ibm.team.filesystem.cli.client.internal.subcommands.AcceptCmd;
import com.ibm.team.filesystem.cli.client.internal.subcommands.AcceptCmdOptions;
import com.ibm.team.filesystem.cli.core.AbstractSubcommand;
import com.ibm.team.filesystem.cli.core.subcommands.CommonOptions;
import com.ibm.team.filesystem.cli.core.subcommands.IScmClientConfiguration;
import com.ibm.team.rtc.cli.infrastructure.internal.parser.ICommandLine;
import com.ibm.team.rtc.cli.infrastructure.internal.parser.Options;
import com.ibm.team.rtc.cli.infrastructure.internal.parser.exceptions.ConflictingOptionException;

@SuppressWarnings("restriction")
public class AcceptCommandDelegate extends RtcCommandDelegate {

  public AcceptCommandDelegate(IScmClientConfiguration config, String targetWorkspace, String changeSetUuid, boolean baseline) {
    super(config);
    setSubCommandLine(targetWorkspace, changeSetUuid, baseline);
  }

  @Override
  AbstractSubcommand getCommand() {
    return new AcceptCmd();
  }

  @Override
  Options getOptions() throws ConflictingOptionException {
    return new AcceptCmdOptions().getOptions();
  }

  void setSubCommandLine(String targetWorkspace, String changeSetUuid, boolean isBaseline) {
    String uri = getSubCommandOption(config, CommonOptions.OPT_URI);
    String username = getSubCommandOption(config, CommonOptions.OPT_USERNAME);
    String password = getSubCommandOption(config, CommonOptions.OPT_PASSWORD);
    setSubCommandLine(config, generateCommandLine(uri, username, password, targetWorkspace, changeSetUuid, isBaseline));
  }

  private ICommandLine generateCommandLine(String uri, String username, String password, String rtcWorkspace, String changeSetUuid,
      boolean isBaseline) {
    List<String> args = new ArrayList<String>();
    args.add("-o");
    args.add("--no-merge");
    args.add("-r");
    args.add(uri);
    args.add("-t");
    args.add(rtcWorkspace);
    args.add("-u");
    args.add(username);
    args.add("-P");
    args.add(password);

    if (isBaseline) {
      args.add("--baseline");
    } else {
      args.add("--changes");
    }
    args.add(changeSetUuid);
    return generateCommandLine(args);
  }

}