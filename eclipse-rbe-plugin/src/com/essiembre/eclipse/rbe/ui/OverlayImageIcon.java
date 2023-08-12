/*
 * Copyright (C) 2003-2014  Pascal Essiembre
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
package com.essiembre.eclipse.rbe.ui;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author Pascal Essiembre
 * 2023-08-12 Gro Replaced deprecated code
 */
public class OverlayImageIcon extends CompositeImageDescriptor {

    /** Constant for overlaying icon in top left corner. */
    public static final int TOP_LEFT = 0;
    /** Constant for overlaying icon in top right corner. */
    public static final int TOP_RIGHT = 1;
    /** Constant for overlaying icon in bottom left corner. */
    public static final int BOTTOM_LEFT = 2;
    /** Constant for overlaying icon in bottom right corner. */
    public static final int BOTTOM_RIGHT = 3;

    private Image baseImage;
    private Image overlayImage;
    private int location;
    private Point imgSize;

    /**
     * Constructor.
     * @param baseImage background image
     * @param overlayImage the image to put on top of background image
     * @param location in which corner to put the icon
     */
    public OverlayImageIcon(Image baseImage, Image overlayImage, int location) {
        super();
        this.baseImage = baseImage;
        this.overlayImage = overlayImage;
        this.location = location;
        this.imgSize = new Point(
                baseImage.getImageData().width,
                baseImage.getImageData().height);
    }

    @Override
    protected void drawCompositeImage(int width, int height) {

        CachedImageDataProvider provider = createCachedImageDataProvider(baseImage);
        drawImage(provider, 0,0);

        // Draw the base image
        provider = createCachedImageDataProvider(overlayImage);

        switch (location) {
            // Draw on the top left corner
            case TOP_LEFT -> drawImage(provider, 0,0);
            // Draw on top right corner
            case TOP_RIGHT -> drawImage(provider, imgSize.x - provider.getWidth(), 0);
            // Draw on bottom left
            case BOTTOM_LEFT -> drawImage(provider, 0, imgSize.y - provider.getHeight());
            // Draw on bottom right corner
            case BOTTOM_RIGHT -> drawImage(provider, imgSize.x - provider.getWidth(),
                imgSize.y - provider.getHeight());
            default -> throw new RuntimeException("Unexpected location: " + location);
        }
    }

    @Override
    protected Point getSize() {
        return imgSize;
    }
}
