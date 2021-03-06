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

import no.trank.openpipe.config.BeanValidator;
import no.trank.openpipe.config.annotation.NotEmpty;

/**
 * A convenient base-implementation of a {@link PipelineStep}.
 *
 * @version $Revision$
 */
public abstract class BasePipelineStep implements PipelineStep {
   @NotEmpty
   private String name;

   public BasePipelineStep() {
      name = getClass().getSimpleName();
   }

   /**
    * Creates a step with the given name.
    *
    * @param name the name of step.
    *
    * @see PipelineStep#getName()
    * @see PipelineStep#setName(String)
    */
   public BasePipelineStep(String name) {
      this.name = name;
   }

   /**
    * Calls {@link BeanValidator#validate(Object) BeanValidator.validate(this)}.
    *
    * @throws PipelineException see {@link BeanValidator#validate(Object)}.
    */
   @Override
   public void prepare() throws PipelineException {
      BeanValidator.validate(this);
   }

   /**
    * Does nothing. Override to implement.
    */
   @Override
   public void finish(boolean success) throws PipelineException {
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public void setName(String name) {
      this.name = name;
   }
}
