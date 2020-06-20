/*
 * Copyright 2017 life.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vishnu.Calc.IO;

/**
 *
 * @author life
 */
public class MathConstants {

    public boolean IsMathConstants(String input) {
        return IsTrignamentry(input);
    }

    public boolean IsTrignamentry(String input) {
        return (input.contains("sin") || input.contains("cos") || input.contains("tan") || input.contains("cot") || input.contains("sec"));
    }
    
    public boolean IsLog(String input) {
        return (input.contains("log") || input.contains("cos") || input.contains("tan") || input.contains("cot") || input.contains("sec"));
    }
        public boolean IsGeneralConst(String input) {
        return (input.toLowerCase().contains("pi") || input.contains("f") || input.contains("E") || input.contains("cot") || input.contains("sec"));
    }
}
