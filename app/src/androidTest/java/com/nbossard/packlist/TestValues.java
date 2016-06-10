/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nbossard.packlist;

/**
 * Constants used in multiple GUI tests.
 */
public class TestValues
{

    /** Delay to let UI thread update its display peacefully. Milliseconds. */
    public static final int LET_UI_THREAD_UPDATE_DISPLAY = 1000;

    /** Delay to let human read. Milliseconds. */
    public static final int LET_HUMAN_TIME_READING = 1000;

    /** To use in wait for nearly nothing (10 milliseconds) */
    public static final int TIMEOUT_10_MS = 10;

    /** To use in wait for ..., wait 1/2 second (500 milliseconds) */
    public static final int TIMEOUT_500_MS = 1000;

    /** To use in wait for ..., wait one second (1000 milliseconds) */
    public static final int TIMEOUT_1000_MS = 1000;

    /** Let developer think about it. */
    @SuppressWarnings("unused")
    public static final int LET_HUMAN_TIME_DEBUG = 4000;

    /** view visibility must not be GONE. */
    public static final boolean ONLY_VISIBLE = true;

    /** Use in searchForText, scroll down if required. */
    public static final boolean SCROLL_DOWN = true;

    /** Exactly one match expected. */
    public static final int ONE_MATCH = 1;

    /** Do not scroll in page. */
    public static final boolean DO_NOT_SCROLL = false;
}
