package io.github.nano.devilry.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
//fixme
//todo

public class OwlMoveControl extends FlyingMoveControl
{

    public OwlMoveControl(Mob p_i225710_1_, int p_i225710_2_, boolean p_i225710_3_)
    {
        super(p_i225710_1_, p_i225710_2_, p_i225710_3_);
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
            if (this.mob.onGround())
            {
                f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }
            else
            {
                f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            }

            this.mob.setSpeed(f1);
            double d4 = Mth.invSqrt(d0 * d0 + d2 * d2);
            float f2 = (float)(-(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
            int field_226323_i_ = 0;
            this.mob.xRotO = this.rotlerp(this.mob.xRotO, f2, (float) field_226323_i_);
            this.mob.setYya(d1 > 0.0D ? f1 : -f1);
        }
        else
        {
            boolean field_226324_j_ = false;
            if (!field_226324_j_)
            {
                this.mob.setNoGravity(false);
            }

            this.mob.setYya(2.0F);
            this.mob.setZza(2.0F);
        }
    }
}
