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


/**
 * Check if a temporal expression is in one temporal expression but not another.
 */
public class Difference extends TemporalExpression implements Serializable{
    
	private TemporalExpression included;
	private TemporalExpression excluded;

	public Difference(TemporalExpression included, TemporalExpression excluded) {
		this.included = included;
		this.excluded = excluded;
	}

	@Override
	public boolean includes(LocalDateTime aDate) {
		return included.includes(aDate) && !excluded.includes(aDate);
	}

    public TemporalExpression getIncluded() {
        return included;
    }

    public void setIncluded(TemporalExpression included) {
        this.included = included;
    }

    public TemporalExpression getExcluded() {
        return excluded;
    }

    public void setExcluded(TemporalExpression excluded) {
        this.excluded = excluded;
    }
        
        
        
        @Override
    public String info() {
        return "Difference:\n[included:\n\t" +getIncluded().info()+"]\n" 
                + "[excluded:" +getExcluded().info()+"]\n";
    }

}
