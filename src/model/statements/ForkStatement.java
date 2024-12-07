package model.statements;

import exceptions.MyException;
import model.states.ProgramState;

public class ForkStatement implements IStatement {
    private final IStatement inner;

    public ForkStatement(IStatement inner) {
        this.inner = inner;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return new ProgramState(
                state.getSymbolsTable().deepCopy(),
                state.getOutput(),
                state.getFileTable(),
                state.getHeap(),
                inner);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(inner.deepCopy());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("FORK\n", inner));
        builder.append(String.format("    %s\n", inner));
        builder.append("    END FORK");
        return builder.toString();
    }
}
