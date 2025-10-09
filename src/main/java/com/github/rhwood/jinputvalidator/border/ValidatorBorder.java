/*
 * Copyright (C) 2019, 2022 Randall Wood
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
package com.github.rhwood.jinputvalidator.border;

import com.github.rhwood.jinputvalidator.Validation;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * A CompoundBorder that draws an inner border that contains the validation
 * icon to the right of the input being validated. The outer border is the
 * original border of the component this border is attached to.
 *
 * @author Randall Wood
 */
public class ValidatorBorder extends CompoundBorder {

    /**
     * Serial version UID. (required since extending java.io.Serializable)
     */
    private static final long serialVersionUID = 1L;
    /**
     * The validation to use in the border. (f for "field" to avoid conflict
     * with parameter name)
     */
    private final transient Validation fValidation;
    /**
     * The font used to draw the validation icons.
     */
    private Font font;
    /**
     * Multiplier to apply to icon width and border width to determine
     * insets and position.
     */
    private final float multiplier = 1.5f;

    /**
     * Create a ValidatorBorder.
     *
     * @param validation     the validation to use in the border; must not be
     *                       null
     * @param originalBorder the original border of the component being
     *                       validated
     */
    public ValidatorBorder(
        final Validation validation,
        final Border originalBorder) {
        this.fValidation = validation;
        this.font = this.fValidation.getFont().deriveFont(Font.BOLD, 0);
        this.outsideBorder = originalBorder;
        this.insideBorder = new AbstractBorder() {
            private static final long serialVersionUID = 1L;

            /**
             * {@inheritDoc}
             */
            @Override
            public void paintBorder(
                final Component c,
                final Graphics g,
                final int x,
                final int y,
                final int width,
                final int height) {
                Insets insets = outsideBorder.getBorderInsets(c);
                FontMetrics metrics = getFontMetrics(c);
                int by = (c.getHeight() / 2)
                    + (metrics.getAscent() / 2)
                    - insets.top;
                // border width
                int bw = Math.max(2, insets.right);
                // icon width
                int iw = metrics.stringWidth(validation.getIcon());
                int bx = x
                    + width
                    - Math.round((iw * multiplier) + (bw * multiplier))
                    + 2;
                g.translate(bx, by);
                g.setColor(validation.getColor());
                g.setFont(font);
                if (g instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(
                            RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setRenderingHint(
                            RenderingHints.KEY_FRACTIONALMETRICS,
                            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                }
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
            public Insets getBorderInsets(
                final Component c,
                final Insets insets) {
                FontMetrics metrics = getFontMetrics(c);
                // icon width
                int iw = metrics.stringWidth(validation.getIcon());
                insets.right = Math.round(iw * multiplier);
                return insets;
            }
        };
    }

    private FontMetrics getFontMetrics(final Component c) {
        Font cFont = c.getFont();
        if (font.getSize() != cFont.getSize()) {
            font = fValidation.getFont().deriveFont(Font.BOLD, cFont.getSize());
        }
        return c.getFontMetrics(font);
    }
}
