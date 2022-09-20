package com.m4rc310.rcp.ui.utils.appinfo;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;



/**
 * Stores information about the product.  This class replaces the old AboutInfo.
 * The product information is available as strings, but is needed as URLs, etc.
 * This class manages that translation.
 * @since 3.0
 */
public class E4ProductInfo {
    private IProduct product;

    private String productName;

    private String appName;

	private Version appVersion;

    private ImageDescriptor[] windowImages;

    private ImageDescriptor aboutImage;

    private String aboutText;

    public E4ProductInfo(IProduct product) {
        this.product = product;
    }

    /**
     * Returns the product name or <code>null</code>.
     * This is shown in the window title and the About action.
     *
     * @return the product name, or <code>null</code>
     */
    public String getProductName() {
        if (productName == null && product != null) {
			productName = product.getName();
		}
        return productName;
    }

    /**
     * Returns the application name or <code>null</code>. Note this is never
     * shown to the user.  It is used to initialize the SWT Display.
     * <p>
     * On Motif, for example, this can be used to set the name used
     * for resource lookup.
     * </p>
     *
     * @return the application name, or <code>null</code>
     * 
     * @see org.eclipse.swt.widgets.Display#setAppName
     */
    public String getAppName() {
        if (appName == null && product != null) {
			appName = E4ProductProperties.getAppName(product);
		}
        return appName;
    }

	/**
	 * Return the application version, as defined by the product.
	 * 
	 * @return the application version, or the empty version.
	 * @see org.eclipse.swt.widgets.Display#setAppVersion
	 * @see Version#emptyVersion
	 * @since 3.6
	 */
	public String getAppVersion() {
		if (appVersion == null) {
			if (product != null) {
				Bundle bundle = product.getDefiningBundle();
				if (bundle != null) {
					appVersion = bundle.getVersion();
				}
			}
			if (appVersion == null) {
				// if we can't find a useful product bundle, try and return
				// the org.eclipse.ui version (approx of the workbench)
				Bundle bundle = Platform.getBundle("org.eclipse.e4.ui.workbench"); //$NON-NLS-1$
				appVersion = bundle == null ? Version.emptyVersion : bundle.getVersion();
			}
		}
		return appVersion.toString();
	}

    /**
     * Returns the descriptor for an image which can be shown in an "about" dialog 
     * for this product. Products designed to run "headless" typically would not 
     * have such an image.
     * 
     * @return the descriptor for an about image, or <code>null</code> if none
     */
    public ImageDescriptor getAboutImage() {
        if (aboutImage == null && product != null) {
			aboutImage = E4ProductProperties.getAboutImage(product);
		}
        return aboutImage;
    }

    /**
     * Return an array of image descriptors for the window images to use for
     * this product. The expectations is that the elements will be the same
     * image rendered at different sizes. Products designed to run "headless"
     * typically would not have such images.
     * 
     * @return an array of the image descriptors for the window images, or
     *         <code>null</code> if none
     */
    public ImageDescriptor[] getWindowImages() {
        if (windowImages == null && product != null) {
			windowImages = E4ProductProperties.getWindowImages(product);
		}
        return windowImages;
    }

    /**
     * Returns the text to show in an "about" dialog for this product.
     * Products designed to run "headless" typically would not have such text.
     * 
     * @return the about text, or <code>null</code> if none
     */
    public String getAboutText() {
        if (aboutText == null && product != null) {
			aboutText = E4ProductProperties.getAboutText(product);
		}
        return aboutText;
    }
}
