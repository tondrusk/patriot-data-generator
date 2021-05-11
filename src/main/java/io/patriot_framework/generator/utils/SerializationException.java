/*
 * Copyright 2021 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.generator.utils;

/**
 * Exception representing problem with the serialization of the device or active.
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadis</a>
 */
public class SerializationException extends RuntimeException {
    /**
     * Default constructor
     */
    public SerializationException() {
    }

    /**
     * Constructor with message of the exception
     *
     * @param s message of the exception
     */
    public SerializationException(String s) {
        super(s);
    }

    /**
     * Constructor with message and throwable
     *
     * @param s         message of the exception
     * @param throwable Throwable object
     */
    public SerializationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Constructor with throwable parameter
     *
     * @param throwable Throwable object
     */
    public SerializationException(Throwable throwable) {
        super(throwable);
    }

}
