/**
 * Copyright 2017 Emmanuel Bourg
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

package net.jsign;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Console implementation for the command line tool.
 * 
 * @author Emmanuel Bourg
 * @since 2.0
 */
class StdOutConsole implements Console {

    public void debug(String message) {
        Logger.getLogger(StdOutConsole.class.getName()).log(Level.INFO, message);
    }

    public void info(String message) {
        Logger.getLogger(StdOutConsole.class.getName()).log(Level.INFO, message);
    }

    public void warn(String message) {
        warn(message, null);
    }

    public void warn(String message, Throwable t) {
        Logger.getLogger(StdOutConsole.class.getName()).log(Level.SEVERE, message);
        if (t != null) {
            t.printStackTrace(System.err);
        }
    }
}
