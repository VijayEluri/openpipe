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
public class MultiPipelineException extends PipelineException {
   private Throwable lastThrowable = this;

   public MultiPipelineException() {
   }

   public MultiPipelineException(String pipelineStepName) {
      super((String) null, pipelineStepName);
   }

   public boolean add(Throwable throwable) {
      if (throwable == null) {
         return false;
      }
      lastThrowable.initCause(throwable);
      lastThrowable = throwable;
      while (lastThrowable.getCause() != null) {
         lastThrowable = lastThrowable.getCause();
      }
      return true;
   }
}
