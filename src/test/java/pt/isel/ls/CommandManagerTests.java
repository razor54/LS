package pt.isel.ls;

import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.model.exceptions.CommandNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static pt.isel.ls.control.CommandManager.findCommand;

public class CommandManagerTests {

    private String cmd;

    @Before
    public void beforeMethod() {

    }

    @Test
    public void testGetOneElementPath() {
        String command = "GET /students";
        System.out.println(command);


        findCommand(command);
    }

    @Test
    public void testGetTwoElementPathWithVariable() {
        String command = "GET /students/1";
        System.out.println(command);

        CommandExecuter c = findCommand(command);
        assertNotNull(c);
    }

    @Test
    public void testGetThreeElementPathWithVariable() {
        String command = "GET /courses/ls/classes";
        System.out.println(command);

        CommandExecuter c = findCommand(command);
        assertNotNull(c);
    }

    @Test
    public void testGetFourElementPathWithVariables() {
        String command = "GET /courses/ls/classes/2";
        System.out.println(command);

        CommandExecuter c = findCommand(command);
        assertNotNull(c);
    }

    @Test
    public void testGetFiveElementPathWithVariables() {
        String command = "GET /courses/ls/classes/2/42000";
        System.out.println(command);

        CommandExecuter c = findCommand(command);
        assertNotNull(c);
    }

    @Test
    public void testGetSixElementPathWithVariables() {
        String command = "GET /courses/ls/classes/2/42000/teachers";
        System.out.println(command);

        CommandExecuter c = findCommand(command);
        assertNotNull(c);
    }

    @Test(expected = CommandNotFoundException.class)
    public void shouldAssertNull_NotValidCmd() {
        cmd = "arrayOutBounds";

        CommandExecuter c = findCommand(cmd);
        assertNull(c);

    }

    @Test(expected = CommandNotFoundException.class)
    public void shouldAssertNull_NoValidStringMethodAndPath() {
        cmd = "waittingFor nullPointer";

        CommandExecuter c = findCommand(cmd);
        assertNull(c);
    }

    @Test(expected = CommandNotFoundException.class)
    public void shouldAssertNull_NoValidStringCMDWithArgs() {
        cmd = "wattingFor null pointer";
        CommandExecuter c = findCommand(cmd);
        assertNull(c);
    }

    @Test(expected = CommandNotFoundException.class)
    public void shouldAssertNull_NoValidPath() {

        cmd = "GET /random ";
        CommandExecuter c = findCommand(cmd);
        assertNull(c);
    }

    @Test(expected = CommandNotFoundException.class)
    public void shouldAssertNull_NoValidPathNorArgs() {
        cmd = "GET /random things";
        CommandExecuter c = findCommand(cmd);
        assertNull(c);
    }

    @Test
    public void shouldFindCmdWithoutArgs() {
        cmd = "GET /teachers";
        CommandExecuter c = findCommand(cmd);
        assertNotNull(c);
    }
}
