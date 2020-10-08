package classLoader;

import java.awt.*;

/**
 * This class simplifies the use of the GridBagConstraints
 * class.
 * Created by shucheng on 2020/10/3 20:53
 */
public class GBC extends GridBagConstraints {

    /**
     * Constructs a GBC with a given gridx and gridy position and
     * all other grid bag constraint values set to the default.
     * @param gridx the gridx position
     * @param gridy the gridy position
     */
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * Constructs a GBC with give gridx, gridy, gridwidth, gridheight
     * and all other grid bag constraint values set to the default.
     * @param gridx
     * @param gridy
     * @param gridwidth
     * @param gridheight
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    /**
     * Sets the anchor
     * @param anchor the anchor value
     * @return this object for futher modification
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * Sets the fill direction
     * @param fill the fill direction
     * @return this object for futher modification
     */
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    /**
     * Sets the cell weights.
     * @param weightx the cell weight in x-direction
     * @param weighty the cell weight in x-direction
     * @return this object for futher modification
     */
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * Sets the insets of this cell.
     * @param distance the spacing to use in all directions
     * @return this object for futher modification
     */
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * Sets the insets of this cell.
     * @param top the spacing to use on top
     * @param left the spacing to use to the left
     * @param bottom the spacing to use ont the bottom
     * @param right the spacing to use to the right
     * @return this object for futher modification
     */
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * Sets the internal padding
     * @param ipadx the internal padding in x-direction
     * @param ipady the internal padding in y-direction
     * @return this object for futher modification
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
