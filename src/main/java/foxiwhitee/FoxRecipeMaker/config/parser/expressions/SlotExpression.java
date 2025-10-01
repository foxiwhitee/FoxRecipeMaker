package foxiwhitee.FoxRecipeMaker.config.parser.expressions;

import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;

public class SlotExpression implements Expression {
    private final int id, x, y;
    private final boolean big, decorate;

    public SlotExpression(int x, int y, boolean big, boolean decorate) {
        this.id = 0;
        this.x = x;
        this.y = y;
        this.big = big;
        this.decorate = decorate;
    }

    public SlotExpression(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.big = false;
        this.decorate = false;
    }

    @Override
    public Object eval() {
        return new ContainerInformation.Slot(id, x, y, big, decorate);
    }
}
