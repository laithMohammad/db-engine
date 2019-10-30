package statement;

public class ExecutableStatement {
	private Statement statement;
	private Row rowToApply;

	public ExecutableStatement(Statement statement, Row rowToApply) {
		this.statement = statement;
		this.rowToApply = rowToApply;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public void setRowToApply(Row rowToApply) {
		this.rowToApply = rowToApply;
	}

	public Statement getStatement() {
		return statement;
	}

	public Row getRowToApply() {
		return rowToApply;
	}
}
