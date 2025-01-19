package model.states;

import model.enums.*;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;

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
                                new AssignStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticOperation.MULTIPLICATION),
                                        ArithmeticOperation.ADDITION)),
                                new CompoundStatement(
                                        new AssignStatement("b", new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticOperation.ADDITION)),
                                        new PrintStatement(new VariableExpression("b"))))));
    }

    public static IStatement ifStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "v"),
                        new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
    }

    public static IStatement switchCaseExample() {
        return new CompoundStatement(new VariableDeclarationStatement(new IntType(), "a"), new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "b"),
                new CompoundStatement(new VariableDeclarationStatement(new IntType(), "c"), new CompoundStatement(
                        new AssignStatement("a", new ValueExpression(new IntValue(1))),
                        new CompoundStatement(new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                new CompoundStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                        new CompoundStatement(new SwitchCaseStatement(
                                                new ArithmeticExpression(new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(10)),
                                                        ArithmeticOperation.MULTIPLICATION),
                                                new ArithmeticExpression(new VariableExpression("b"),
                                                        new VariableExpression("c"),
                                                        ArithmeticOperation.MULTIPLICATION),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                        new PrintStatement(new VariableExpression("b"))),
                                                new ValueExpression(new IntValue(10)),
                                                new CompoundStatement(
                                                        new PrintStatement(new ValueExpression(new IntValue(100))),
                                                        new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                new PrintStatement(new ValueExpression(new IntValue(300)))),
                                                new PrintStatement(new ValueExpression(new IntValue(300))))))))));
    }

    public static IStatement readFromFileExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("build/test.txt"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"),
                                                                        "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(
                                                                                new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(
                                                                                new VariableExpression("varf"))))))))));
    }

    public static IStatement allocateToHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
    }

    public static IStatement readFromHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(new ReadHeapExpression(
                                                                        new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5)),
                                                                ArithmeticOperation.ADDITION)))))));
    }

    public static IStatement writeToHeapExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        ArithmeticOperation.ADDITION))))));
    }

    public static IStatement garbageCollectorExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "v"),
                new CompoundStatement(
                        new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(new IntType())), "a"),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new AllocateHeapStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(
                                                        new AllocateHeapStatement("v",
                                                                new ValueExpression(new IntValue(40))),
                                                        new CompoundStatement(
                                                                new AllocateHeapStatement("v",
                                                                        new ValueExpression(new IntValue(50))),
                                                                new PrintStatement(
                                                                        new ReadHeapExpression(new ReadHeapExpression(
                                                                                new VariableExpression("a")))))))))));
    }

    public static IStatement whileStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                ArithmeticRelation.GREATER_THAN),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                ArithmeticOperation.SUBTRACTION)))),
                                new PrintStatement(new VariableExpression("v")))));
    }

    public static IStatement repeatUntilExample() {
        return new CompoundStatement(new VariableDeclarationStatement(new IntType(), "v"), new CompoundStatement(
                new AssignStatement("v", new ValueExpression(new IntValue(0))),
                new CompoundStatement(
                        new RepeatUntilStatement(
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new AssignStatement("v",
                                                                new ArithmeticExpression(new VariableExpression("v"),
                                                                        new ValueExpression(new IntValue(1)),
                                                                        ArithmeticOperation.SUBTRACTION)))),
                                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), ArithmeticOperation.ADDITION))),
                                new RelationalExpression(new VariableExpression("v"),
                                        new ValueExpression(new IntValue(3)), ArithmeticRelation.EQUAL)),
                        new CompoundStatement(new VariableDeclarationStatement(new IntType(), "x"),
                                new CompoundStatement(new VariableDeclarationStatement(new IntType(), "y"),
                                        new CompoundStatement(new VariableDeclarationStatement(new IntType(), "z"),
                                                new CompoundStatement(
                                                        new VariableDeclarationStatement(new IntType(), "w"),
                                                        new CompoundStatement(
                                                                new AssignStatement("x",
                                                                        new ValueExpression(new IntValue(1))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("y",
                                                                                new ValueExpression(new IntValue(2))),
                                                                        new CompoundStatement(
                                                                                new AssignStatement("z",
                                                                                        new ValueExpression(
                                                                                                new IntValue(3))),
                                                                                new CompoundStatement(
                                                                                        new AssignStatement("w",
                                                                                                new ValueExpression(
                                                                                                        new IntValue(
                                                                                                                4))),
                                                                                        new PrintStatement(
                                                                                                new ArithmeticExpression(
                                                                                                        new VariableExpression(
                                                                                                                "v"),
                                                                                                        new ValueExpression(
                                                                                                                new IntValue(
                                                                                                                        10)),
                                                                                                        ArithmeticOperation.MULTIPLICATION)))))))))))));
    }

    public static IStatement forStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(new IntType()), "a"),
                new CompoundStatement(
                        new AllocateHeapStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new ForStatement(
                                        "v",
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), ArithmeticOperation.ADDITION),
                                        new ForkStatement(new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression(new VariableExpression("v"),
                                                                new ReadHeapExpression(new VariableExpression("a")),
                                                                ArithmeticOperation.MULTIPLICATION))))),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));
    }

    public static IStatement conditionalAssignmentExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new BoolType(), "b"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntType(), "c"),
                        new CompoundStatement(
                                new AssignStatement("b", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new ConditionalAssignmentStatement("c", new VariableExpression("b"),
                                                new ValueExpression(new IntValue(100)),
                                                new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("c")),
                                                new CompoundStatement(
                                                        new ConditionalAssignmentStatement("c",
                                                                new ValueExpression(new BoolValue(false)),
                                                                new ValueExpression(new IntValue(100)),
                                                                new ValueExpression(new IntValue(200))),
                                                        new PrintStatement(new VariableExpression("c"))))))));
    }

    public static IStatement sleepStatementExample() {
        return new CompoundStatement(new VariableDeclarationStatement(new IntType(), "v"), new CompoundStatement(
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(
                        new ForkStatement(new CompoundStatement(
                                new AssignStatement("v",
                                        new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACTION)),
                                new CompoundStatement(
                                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACTION)),
                                        new PrintStatement(new VariableExpression("v"))))),
                        new CompoundStatement(new SleepStatement(new ValueExpression(new IntValue(10))),
                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"),
                                        new ValueExpression(new IntValue(10)), ArithmeticOperation.MULTIPLICATION))))));
    }

    public static IStatement forkStatementExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(new IntType()), "a"),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new AllocateHeapStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a",
                                                                        new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new VariableExpression("v")),
                                                                                new PrintStatement(
                                                                                        new ReadHeapExpression(
                                                                                                new VariableExpression(
                                                                                                        "a"))))))),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new CompoundStatement(
                                                                new AssignStatement("v",
                                                                        new ValueExpression(new IntValue(40))),
                                                                new PrintStatement(new ReadHeapExpression(
                                                                        new VariableExpression("a"))))))))));
    }
}
