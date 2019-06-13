/*
 * Copyright (C) 2019 Randall Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alexandriasoftware.swing.border;

import com.alexandriasoftware.swing.Validation;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import javax.annotation.Nonnull;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * A CompoundBorder that draws an inner border that contains the validation icon
 * to the right of the input being validated. The outer border is the original
 * border of the component this border is attached to.
 *
 * @author Randall Wood
 */
public class ValidatorBorder extends CompoundBorder {

    private static final long serialVersionUID = 1L;
    private final Validation validation;
    private Font font;

    /**
     * Create a ValidatorBorder.
     *
     * @param validation     the validation to use in the border
     * @param originalBorder the original border of the component being
     *                       validated
     */
    public ValidatorBorder(@Nonnull Validation validation, Border originalBorder) {
        this.validation = validation;
        this.font = this.validation.getFont().deriveFont(Font.BOLD, 0);
        this.outsideBorder = originalBorder;
        this.insideBorder = new AbstractBorder() {
            private static final long serialVersionUID = 1L;

            /**
             * {@inheritDoc}
             */
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Insets insets = outsideBorder.getBorderInsets(c);
                FontMetrics metrics = getFontMetrics(c);
                int by = (c.getHeight() / 2) + (metrics.getAscent() / 2) - insets.top;
                int bw = Math.max(2, insets.right); // border width
                int iw = metrics.stringWidth(validation.getIcon()); // icon width
                int bx = x + width - (Math.round((iw * 1.5f) + (bw * 1.5f))) + 2;
                g.translate(bx, by);
                g.setColor(validation.getColor());
                g.setFont(font);
                g.drawString(validation.getIcon(), x + (iw / 2), y);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isBorderOpaque() {
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                FontMetrics metrics = getFontMetrics(c);
                int iw = metrics.stringWidth(validation.getIcon()); // icon width
                insets.right = Math.round(iw * 1.5f);
                return insets;
            }
        };
    }

    private FontMetrics getFontMetrics(Component c) {
        Font cFont = c.getFont();
        if (font.getSize() != cFont.getSize()) {
            font = validation.getFont().deriveFont(Font.BOLD, cFont.getSize());
        }
        return c.getFontMetrics(font);
    }
}
