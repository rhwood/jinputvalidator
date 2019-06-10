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
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 *
 * @author Randall Wood
 */
public class ValidatorBorder extends AbstractBorder {

    private final Border originalBorder;
    private final Validation validation;

    public ValidatorBorder(Validation validation, Border originalBorder) {
        this.originalBorder = originalBorder;
        this.validation = validation;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        originalBorder.paintBorder(c, g, x, y, width, height);
        Insets insets = getBorderInsets(c);
        Font font = c.getFont();
        FontMetrics metrics = c.getFontMetrics(font);
        int by = (c.getHeight() / 2) + (metrics.getAscent() / 2);
        int bw = Math.max(2, insets.left); // border width
        int iw = metrics.stringWidth(validation.getIcon()); // icon width
        int bx = x + width - (Math.round(iw * 1.5f) + (bw * 2)) + 2;
        g.translate(bx, by);
        g.setColor(validation.getColor());
        g.setFont(validation.getFont().deriveFont(Font.BOLD, font.getSize()));
        g.drawString(validation.getIcon(), x, y);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return originalBorder.getBorderInsets(c);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
