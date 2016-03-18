/**

 * Title: SuperCtlCmdConfigFactory.java

 * Description: 

 * Copyright: ByTom's Studio 2016

 *            All right reserved.

 * 2016年2月23日
 */
package com.smart.control.entity.command;

import com.smart.control.entity.codeset.SuperCtlCodeSet;
import com.smart.control.entity.endecoder.SuperEnDecoder;
import com.smart.control.entity.netclient.ISuperNetClient;

/**
 * 
 * @职责 
 * @属层 
 * @author ByTom
 */
public abstract class SuperCtlCmdConfigFactory {

	abstract public SuperCtlCodeSet createCodeSet();
	abstract public SuperEnDecoder createEnDecoder();
	abstract public ISuperNetClient createNetClient();
}
