/*
 * The MIT License
 *
 * Copyright 2019-03-01 automated.ch, Tobi Tiggers.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ch.automated.temporal.operations;

import ch.automated.temporal.TemporalExpression;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Intersection extends TemporalExpression implements Serializable {

    List<TemporalExpression> elements = new ArrayList<>();

    @Override
    public boolean includes(LocalDateTime aDate) {
        for (TemporalExpression e : elements) {
            if (!e.includes(aDate)) {
                return false;
            }
        }
        return true;
    }

    public void add(TemporalExpression e) {
        elements.add(e);
    }

    public List<TemporalExpression> getElements() {
        return elements;
    }

    public void setElements(List<TemporalExpression> elements) {
        this.elements = elements;
    }

    @Override
    public String info() {
        String info = "Intersection:\n";
        for (TemporalExpression element : getElements()) {
            info = info + "\t" + element.info();
        }
        return info;
    }

}
