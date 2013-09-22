package com.apeironsol.need.core;



import org.junit.Before;
import org.junit.Test;
 

//@TransactionConfiguration(defaultRollback = false)
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/test-app-context.xml")
public class BuildingBlockTest {

//	@Resource
	//private BuildingBlockService						buildingBlockService;

	//@Resource
	//private ServletContextUtils							servletContextUtils;

	//private BuildingBlock								buildingBlock;

	//protected static ConfigurableWebApplicationContext	context;

	@Before
	public void setUpServletContext() {
	/*	if (ServletContextUtils.getServletContext() == null) {
			final MockServletContext servletContext = new MockServletContext();
			servletContext.addInitParameter(ContextParams.SYSTEM_TIMEZONE, "Asia/Kolkata");
			this.servletContextUtils.setServletContext(servletContext);
		}*/
	}

	@Test
	public void sampleTest() {
		/*System.out.println("Number of rows now is: ");
		this.buildingBlock = new BuildingBlock();
		this.buildingBlock.setName("Tution Fee");
		this.buildingBlock.setCode("FT_TUTFEE");
		this.buildingBlock.setType(BuildingBlockConstant.FEE_TYPE);
		this.buildingBlock.setFeeClassificationLevel(FeeClassificationLevelConstant.KLASS_LEVEL);
		this.buildingBlock.setFeeType(FeeTypeConstant.TUITION_FEE);
		this.buildingBlock.setMandatory(true);
		this.buildingBlock.setNewAdmissionFee(false);
		final BuildingBlock result = this.buildingBlockService.saveBuildingBlock(this.buildingBlock);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		System.out.println("Number of rows now is: ");*/
	}
}
