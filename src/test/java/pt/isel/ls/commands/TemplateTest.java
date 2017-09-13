package pt.isel.ls.commands;

import org.junit.Test;
import pt.isel.ls.model.commands.CommandTemplate;
import pt.isel.ls.utils.Template;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TemplateTest {

    private Template template;

    @Test
    public void testTEmplateFindInvalidTemplate() {
        template = Template.of(CommandTemplate::isTemplateOf,"GET /teachers/{num}",null);
        boolean notTemplate = template.isTemplateOf("GET /all");
        assertFalse(notTemplate);
    }

    @Test
    public void testFindValidTemplate() {
        template = Template.of(CommandTemplate::isTemplateOf, "GET /teachers",null);
        boolean notTemplate = template.isTemplateOf("GET /teachers");
        assertTrue(notTemplate);
    }


}