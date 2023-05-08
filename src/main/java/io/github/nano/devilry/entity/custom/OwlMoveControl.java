package io.github.nano.devilry.entity.custom;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
//fixme
//todo

public class OwlMoveControl extends FlyingMoveControl
{
    private int field_226323_i_ = 0;
    private boolean field_226324_j_ = false;

    public OwlMoveControl(Mob p_i225710_1_, int p_i225710_2_, boolean p_i225710_3_)
    {
        super(p_i225710_1_, p_i225710_2_, p_i225710_3_);
        this.field_226323_i_ = field_226323_i_;
        this.field_226324_j_ = field_226324_j_;
    }

    @Override
    public void tick()
    {
        if (this.operation == Operation.MOVE_TO)
        {
            this.operation = Operation.WAIT;
            this.mob.setNoGravity(true);
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedY - this.mob.getY();
            double d2 = this.wantedZ - this.mob.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double)2.5000003E-7F)
            {
                this.mob.setYya(2.0F);
                this.mob.setZza(2.0F);
                return;
            }

            float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.mob.yRotO = this.rotlerp(this.mob.yRotO, f, 90.0F);
            float f1;
            if (this.mob.isOnGround())
            {
                f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }
            else
            {
                f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            }

            this.mob.setSpeed(f1);
            double d4 = Mth.fastInvSqrt(d0 * d0 + d2 * d2);
            float f2 = (float)(-(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
            this.mob.xRotO = this.rotlerp(this.mob.xRotO, f2, (float)this.field_226323_i_);
            this.mob.setYya(d1 > 0.0D ? f1 : -f1);
        }
        else
        {
            if (!this.field_226324_j_)
            {
                this.mob.setNoGravity(false);
            }

            this.mob.setYya(2.0F);
            this.mob.setZza(2.0F);
        }
    }
}
