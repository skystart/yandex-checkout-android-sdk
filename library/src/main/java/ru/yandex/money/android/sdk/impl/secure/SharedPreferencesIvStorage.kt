/*
 * The MIT License (MIT)
 * Copyright © 2018 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package ru.yandex.money.android.sdk.impl.secure

import android.content.SharedPreferences
import android.util.Base64
import ru.yandex.money.android.sdk.impl.extensions.remove
import ru.yandex.money.android.sdk.impl.extensions.set
import java.security.SecureRandom

internal class SharedPreferencesIvStorage(
        private val sp: SharedPreferences
) : Storage<ByteArray> {

    override fun get(key: String) = sp.getString(key, null)?.let { Base64.decode(it, Base64.DEFAULT) }

    override fun getOrCreate(key: String) = get(key) ?: generateBytes(16).also {
        sp[key] = Base64.encodeToString(it, Base64.DEFAULT)
    }

    override fun remove(key: String) = sp.remove(key)

    private fun generateBytes(length: Int) = ByteArray(length).also {
        SecureRandom().nextBytes(it)
    }
}