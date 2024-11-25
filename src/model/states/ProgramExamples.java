package model.states;

import model.enums.ArithmeticOperation;
import model.enums.ArithmeticRelation;
import model.expressions.ArithmeticExpression;
import model.expressions.ReadHeapExpression;
import model.expressions.RelationalExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.AllocateHeapStatement;
import model.statements.AssignStatement;
import model.statements.CloseReadFileStatement;
import model.statements.CompoundStatement;
import model.statements.IStatement;
import model.statements.IfStatement;
import model.statements.OpenReadFileStatement;
import model.statements.PrintStatement;
import model.statements.ReadFileStatement;
import model.statements.VariableDeclarationStatement;
import model.statements.WhileStatement;
import model.statements.WriteHeapStatement;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public final class ProgramExamples {
    public static IStatement printValueExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(5))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    public static IStatement arithmeticOperationsExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "b"),
                        new CompoundStatement(
                                new AssignStatement("a",
                                        new ArithmeticExpression(
                                                new ValueExpression(
                                                        new IntValue(2)),
                                                new ArithmeticExpression(
                                                        new ValueExpression(
                                                                new IntValue(3)),
                                                        new ValueExpression(
                                                                new IntValue(5)),
                                                        ArithmeticOperation.MULTIPLICATION),
                                                ArithmeticOperation.ADDITION)),
                                new CompoundStatement(
                                        new AssignStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression(
                                                                "a"),
                                                        new ValueExpression(
                                                                new IntValue(1)),
                                                        ArithmeticOperation.ADDITION)),
                                        new PrintStatement(
                                                new VariableExpression(
                                                        "b"))))));
    }

    public static IStatement ifStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new CompoundStatement(
                                new AssignStatement("a",
                                        new ValueExpression(
                                                new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression(
                                                        "a"),
                                                new AssignStatement("v",
                                                        new ValueExpression(
                                                                new IntValue(2))),
                                                new AssignStatement("v",
                                                        new ValueExpression(
                                                                new IntValue(3)))),
                                        new PrintStatement(
                                                new VariableExpression(
                                                        "v"))))));
    }

    public static IStatement readFromFileExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignStatement("varf",
                                new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(
                                        new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(
                                                new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(
                                                        new VariableExpression(
                                                                "varf"),
                                                        "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(
                                                                new VariableExpression(
                                                                        "varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(
                                                                        new VariableExpression(
                                                                                "varf"),
                                                                        "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(
                                                                                new VariableExpression(
                                                                                        "varc")),
                                                                        new CloseReadFileStatement(
                                                                                new VariableExpression(
                                                                                        "varf"))))))))));
    }

    public static IStatement allocateToHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(
                                        new RefType(new RefType(new IntType())),
                                        "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a",
                                                new VariableExpression(
                                                        "v")),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new VariableExpression(
                                                                "v")),
                                                new PrintStatement(
                                                        new VariableExpression(
                                                                "a")))))));
    }

    public static IStatement readFromHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(
                                        new RefType(new RefType(new IntType())),
                                        "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a",
                                                new VariableExpression(
                                                        "v")),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new VariableExpression(
                                                                        "v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(
                                                                        new ReadHeapExpression(
                                                                                new VariableExpression(
                                                                                        "a"))),
                                                                new ValueExpression(
                                                                        new IntValue(5)),
                                                                ArithmeticOperation.ADDITION)))))));
    }

    public static IStatement writeToHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(
                                        new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v",
                                                new ValueExpression(
                                                        new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(
                                                                new VariableExpression(
                                                                        "v")),
                                                        new ValueExpression(
                                                                new IntValue(5)),
                                                        ArithmeticOperation.ADDITION))))));
    }

    public static IStatement garbageCollectorExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(
                                        new RefType(new RefType(new IntType())),
                                        "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a",
                                                new VariableExpression(
                                                        "v")),
                                        new CompoundStatement(
                                                new AllocateHeapStatement(
                                                        "v",
                                                        new ValueExpression(
                                                                new IntValue(30))),
                                                new CompoundStatement(
                                                        new AllocateHeapStatement(
                                                                "v",
                                                                new ValueExpression(
                                                                        new IntValue(40))),
                                                        new CompoundStatement(
                                                                new AllocateHeapStatement(
                                                                        "v",
                                                                        new ValueExpression(
                                                                                new IntValue(50))),
                                                                new PrintStatement(
                                                                        new ReadHeapExpression(
                                                                                new ReadHeapExpression(
                                                                                        new VariableExpression(
                                                                                                "a")))))))))));
    }

    public static IStatement whileStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression(
                                                        "v"),
                                                new ValueExpression(
                                                        new IntValue(0)),
                                                ArithmeticRelation.GREATER_THAN),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new VariableExpression(
                                                                "v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression(
                                                                        "v"),
                                                                new ValueExpression(
                                                                        new IntValue(1)),
                                                                ArithmeticOperation.SUBTRACTION)))),
                                new PrintStatement(new VariableExpression("v")))));
    }
}
