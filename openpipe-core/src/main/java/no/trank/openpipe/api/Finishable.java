/*
 * Copyright 2007  T-Rank AS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.trank.openpipe.api;

/**
 * @version $Revision$
 */
public interface Finishable {

   /**
    * Finishes the object after a batch of documents.
    *
    * @param success if the previous batch of documents succeeded.
    *
    * @throws no.trank.openpipe.api.PipelineException if the object could not finish successfully.
    */
   void finish(boolean success) throws PipelineException;
}