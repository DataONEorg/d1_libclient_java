/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.dataone.client.ObjectFormatCache;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.ObjectFormatList;

import org.junit.Test;

/**
 * Test the ObjectFormatCache to retrieve the object format list, a single 
 * object format, and test a known bad format to handle the NotFound exception.
 * @author cjones
 *
 */
public class ObjectFormatCacheTest {
	
  @Test
  public void testHarnessCheck() {
      assertTrue(true);
  }
  
  /**
   * Test getting the entire object format list.  The default list has at least
   * 31 entries.
   */
  @Test
  public void testListFormats() {
  	
  	int formatsCount = 59;
  	ObjectFormatList objectFormatList;

    try {
	    objectFormatList = ObjectFormatCache.getInstance().listFormats();
	  	assertTrue(objectFormatList.getTotal() >= formatsCount);

    } catch (ServiceFailure e) {
      fail("The service failed: " + e.getMessage());
 
    }
  }
  
  /**
   * Test getting a single object format from the registered list
 * @throws NotImplemented 
 * @throws InsufficientResources 
 * @throws NotFound 
 * @throws ServiceFailure 
 * @throws InvalidRequest 
   */
  @Test
  public void testGetFormatFromString() throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources, NotImplemented {
  	
	  String knownFormat = "text/plain";
	  ObjectFormatIdentifier formatId = new ObjectFormatIdentifier();
	  formatId.setValue(knownFormat);

	  String result = 
		  ObjectFormatCache.getInstance().getFormat(formatId).getFormatId().getValue();
	  assertTrue(result.equals(knownFormat));
  }
  
  /**
   * Test getting a non-existent object format, returning NotFound
   */
  @Test
  public void testObjectFormatNotFoundException() {

	  String badFormat = "text/bad-format";
	  ObjectFormatIdentifier formatId = new ObjectFormatIdentifier();
	  formatId.setValue(badFormat);
	  try {

		  String result = 
			  ObjectFormatCache.getInstance().getFormat(formatId).getFormatId().getValue();

	  } catch (Exception e) {
		  assertTrue(e instanceof NotFound);
	  }
  }
}
