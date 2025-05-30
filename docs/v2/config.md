# Configuration

Chili Bullet Weapons Version 2.0.0, and CBW Chili Peppers and Foods Version 1.0.0

- [Top Page](../index.html)
  - [How to Get Started](index.html)
- ![ ](../media/cpaf_icon_16.png) CBW Chili Peppers and Foods
  - [Farming](farming.html)
  - [Foods](foods.html)
- ![ ](../media/icon_16.png) Chili Bullet Weapons
  - [Weapons](weapons.html)
  - **Configuration**
    - [Common](#common)

The configuration is saved in file `config/chilibulletweapons.toml`.

## Common

The `common` section contains configuration values referenced by both the server and the client.
In multiplayer mode, server-side values are used.

```toml
[common]
```

A Boolean value of `canMultishotMultiHit` represents whether a single target can be damaged multiple times at once by bullets shot from a volley gun.
If set to `false`, a single target will take damage from only one bullet at the same time.

```toml
canMultishotMultiHit = true
```

A real number between 0.0 and 8.0 of `chiliArrowDamageMultiplier` represents the multiplier of the explosive power of bullet chili arrows.

```toml
chiliArrowDamageMultiplier = 1.0
```

A non-negative real number of `chiliBulletBaseDamage` represents the base damage amount of a chili bullet shot from a gun.
If set to `0.0`, chili bullets will do no damage.

```toml
chiliBulletBaseDamage = 0.85
```

Formula for chili bullet damage (only when `chiliBulletBaseDamage` > 0):

```text
# speed (initial) = 3 (pistol/volley gun/machine gun) or 4 (rifle)
# minDamage = ceil(speed * speed * chiliBulletBaseDamage)
# maxDamage = floor(minDamage * 1.5) + 1
# damage = a random integer between minDamage and maxDamage
```
