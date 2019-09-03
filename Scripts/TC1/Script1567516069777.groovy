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

String expr = '''
//*[local-name()="svg"]
  /*[local-name()="g" and @class="sapHpaJourneyChartChannelData"]
    /*[local-name()="g" and @class="sapHpaJourneyChannelData"]
      /*[local-name()="g" and @class="sapHpaJourneyItemIconGroup"]
        /*[local-name()="text" and @x="110"]
'''

TestObject tObj = new TestObject()
tObj.addProperty("xpath", ConditionType.EQUALS, expr)

WebUI.verifyElementPresent(tObj, 10, FailureHandling.STOP_ON_FAILURE)
def text = WebUI.getText(tObj)
WebUI.comment("text is '${text}'")

WebUI.closeBrowser()