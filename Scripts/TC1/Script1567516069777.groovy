import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path html = projectDir.resolve("Include/resources/fixture/saved.html")
URL htmlURL = html.toFile().toURI().toURL()

WebUI.openBrowser('')

WebUI.navigateToUrl(htmlURL.toExternalForm())

// long path
String expr = '''
//*[local-name()="svg"]
  /*[local-name()="g" and @class="sapHpaJourneyChartChannelData"]
    /*[local-name()="g" and @class="sapHpaJourneyChannelData"]
      /*[local-name()="g" and @class="sapHpaJourneyItemIconGroup"]
        /*[local-name()="text" and @x="110"]
'''
def text = getElementText(expr)
WebUI.comment("text is '${text}'")

// short hand example
String expr2 = '''//*[local-name()="text" and @x="110"]'''
def text2 = getElementText(expr2)
WebUI.comment("text2 is '${text2}'")

// this would not work
String expr3 = '''//text[@x="110"]'''
def text3 = getElementText(expr3)
WebUI.comment("text 3 is '${text3}'")

WebUI.closeBrowser()

def getElementText(String xexpr) {
	TestObject tObj = new TestObject(xexpr)
	tObj.addProperty("xpath", ConditionType.EQUALS, xexpr)
	WebUI.verifyElementPresent(tObj, 3, FailureHandling.STOP_ON_FAILURE)
	return WebUI.getText(tObj)
}