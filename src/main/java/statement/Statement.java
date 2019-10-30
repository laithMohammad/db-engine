package statement;

public class Statement {

	private String statementString;
	private StatementType statementType;
	private PrepareResult prepareResult;

	public Statement(String statementString, StatementType statementType, PrepareResult prepareResult) {
		this.statementType = statementType;
		this.prepareResult = prepareResult;
		this.statementString = statementString;
	}

	public StatementType getStatementType() {
		return statementType;
	}

	public String getStatementString() {
		return statementString;
	}

	public PrepareResult getPrepareResult() {
		return prepareResult;
	}
}