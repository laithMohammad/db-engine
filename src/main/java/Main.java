import org.omg.PortableInterceptor.SUCCESSFUL;
import statement.*;

import javax.crypto.spec.PSource;
import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * Clone of SQLite Like DB Engine
 * Front End:
 * Tokenizer
 * Parser
 * Code Generator
 *  The “front-end” of sqlite is a SQL compiler that parses a string and outputs an internal
 *  representation called bytecode.
 *
 * This bytecode is passed to the virtual machine, which executes it.
 *
 *  The output of the above is virtual machine code
 *
 * Virtual Machine
 * B-Tree
 * Pager
 * OS Interface
 */
public class Main {

	private static List<Row> rows;

	public static void main(String[] args) {
		rows = new LinkedList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		while (true) {
			try {
				System.out.print("query>");
				String line = bufferedReader.readLine();
				if(line.startsWith("_")) {
					//Meta command
					if(doMetaCommand(line) == MetaCommand.SUCCESS) {
						return;
					} else {
						System.out.println("Unrecognized Meta Query!!!");
						continue;
					}
				}

				ExecutableStatement executableStatement = doPrepareStatement(line);
				switch (executableStatement.getStatement().getPrepareResult()) {
					case SUCCESS:
						System.out.println("Successfully Query: " + executableStatement.getStatement().getStatementString());
						break;
					case UNRECOGNIZED:
						System.out.println("Unrecognized Query: " + executableStatement.getStatement().getStatementString());
						continue;
					default:
						System.out.println("Internal Error!!!");
						continue;
				}
				executeStatement(executableStatement);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static MetaCommand doMetaCommand(String query) {
		if(query.startsWith("_exit")) {
			System.out.println("Exitting...\nGood By!");
			System.exit(0);
			return MetaCommand.SUCCESS;
		} else {
			return MetaCommand.UNRECOGNIZED;
		}
	}

	private static ExecutableStatement doPrepareStatement(String query) {
		query = query.toUpperCase();
		Row row = extractQueryData(query);
		if( row == null ) {
			System.out.println("Error at extracting data");
		}
		Statement statement = null;
		if(query.startsWith("SELECT")) {
			statement = new Statement(query, StatementType.SELECT, getPrepareResult(row, PrepareResult.SUCCESS));
		} else if(query.startsWith("INSERT")) {
			statement = new Statement(query, StatementType.INSERT, getPrepareResult(row, PrepareResult.SUCCESS));
		} else {
			statement = new Statement(query,null, getPrepareResult(row, PrepareResult.UNRECOGNIZED));
		}
		return new ExecutableStatement(statement, row);
	}

	private static PrepareResult getPrepareResult(Row row, PrepareResult prepareResult) {
		return row != null ? prepareResult : PrepareResult.UNRECOGNIZED;
	}

	private static Row extractQueryData(String query) {
		StringTokenizer stringTokenizer = new StringTokenizer(query, " ");
		if(stringTokenizer.countTokens() < 4) {
			System.out.println("Not Enough Data");
			return null;
		}
		//Query
		stringTokenizer.nextToken();

		//Data
		String id = stringTokenizer.nextToken();
		String userName = stringTokenizer.nextToken();
		String email = stringTokenizer.nextToken();
		return new Row(Integer.valueOf(id), userName, email);
	}

	private static void executeStatement(ExecutableStatement executableStatement) {
		if(executableStatement.getStatement().getStatementType().equals(StatementType.INSERT)) {
			rows.add(executableStatement.getRowToApply());
		} else if(executableStatement.getStatement().getStatementType().equals(StatementType.SELECT)) {
			boolean found = false;
			for (Row row : rows) {
				if(executableStatement.getRowToApply().equals(row)) {
					System.out.println("Selected: " + row);
					found = true;
				}
			}
			if(!found) {
				System.out.println("Not Found!!!");
			}
		}
		System.out.println(executableStatement.getRowToApply());
		System.out.println("Executed!!!");
	}
}




