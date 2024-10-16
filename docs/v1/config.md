# Configuration

- [Top Page](../index.html)
  - [How to Get Started](index.html)
  - [Farming](farming.html)
  - [Foods](foods.html)
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

A Boolean value of `canShotgunMultiHit` represents whether a single target can be damaged multiple times at once by bullets shot from a shotgun.
If set to `false`, a single target will take damage from only one bullet at the same time.

```toml
canShotgunMultiHit = true
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
# speed (initial) = 3 (pistols/shotguns) or 4 (rifles)
# minDamage = ceil(speed * speed * chiliBulletBaseDamage)
# maxDamage = floor(minDamage * 1.5) + 1
# damage = a random integer between minDamage and maxDamage
```
