import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path html = projectDir.resolve("Include/resources/fixture/container.html")
URL htmlURL = html.toFile().toURI().toURL()

WebUI.openBrowser('')
WebUI.navigateToUrl(htmlURL.toExternalForm())

TestObject ifrTO = createTestObject("//div[@id='container']/iframe[1]")
WebUI.waitForElementPresent(ifrTO, 10)
WebUI.switchToFrame(ifrTO, 10)

TestObject svgTitleTO = createTestObject("""
//*[local-name()='svg']//*[local-name()='text' and @class='highcharts-title']
""")
WebUI.verifyElementPresent(svgTitleTO, 10)
String svgTitle = WebUI.getText(svgTitleTO)

if (!svgTitle.startsWith("Solar Employment Growth by Sector")) {
	KeywordUtil.markFailedAndStop("invalid SVG Title") 
}

//WebUI.delay(3)
WebUI.closeBrowser()



TestObject createTestObject(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}