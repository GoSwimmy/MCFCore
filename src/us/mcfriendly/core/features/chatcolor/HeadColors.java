package us.mcfriendly.core.features.chatcolor;

public enum HeadColors {

	DARK_RED("Dark Red", "&4",
			"http://textures.minecraft.net/texture/b439a71957cc1dd4546f4132965effbe3429c889b1c6ba7212cb4b8c03540f8"),
	GREEN("Green", "&2",
			"http://textures.minecraft.net/texture/8e9b27fccd80921bd263c91dc511d09e9a746555e6c9cad52e8562ed0182a2f"),
	BLUE("Blue", "&1",
			"http://textures.minecraft.net/texture/7dd3ede0ad53768abdce493fbf3c2359dc87ec55d2fceeb17754ed590e41a"),
	CYAN("Cyan", "&3",
			"http://textures.minecraft.net/texture/95b9a48467f0212aa68864e6342116f8f79a275454bf215f67f701a6f2c818"),
	PURPLE("Purple", "&5",
			"http://textures.minecraft.net/texture/593f67f9f730d42fda8de69565ea55892c5f85d9cae6dd6fcba5d26f1e7238d1"),
	GOLD("Gold", "&6",
			"http://textures.minecraft.net/texture/2c4886ef362b2c823a6aa65241c5c7de71c94d8ec5822c51e96976641f53ea35"),
	DARK_GRAY("Dark Gray", "&8",
			"http://textures.minecraft.net/texture/608f323462fb434e928bd6728638c944ee3d812e162b9c6ba070fcac9bf9"),
	RED("Red", "&c",
			"http://textures.minecraft.net/texture/5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b"),
	LIME("Lime", "&a",
			"http://textures.minecraft.net/texture/d27ca46f6a9bb89a24fcaf4cc0acf5e8285a66db7521378ed2909ae449697f"),
	LIGHT_BLUE("Light Blue", "&9",
			"http://textures.minecraft.net/texture/f8157b4dc5efc217352894471c116d39a034fc397c24539a9d0eeb2a465ca"),
	BLACK("Black", "&0",
			"http://textures.minecraft.net/texture/967a2f218a6e6e38f2b545f6c17733f4ef9bbb288e75402949c052189ee"),
	PINK("Pink", "&d",
			"http://textures.minecraft.net/texture/c143f422db8e958bbb5de2368692bde6960e4822678b010e24797e7979b5b7"),
	YELLOW("Yellow", "&e",
			"http://textures.minecraft.net/texture/c641682f43606c5c9ad26bc7ea8a30ee47547c9dfd3c6cda49e1c1a2816cf0ba"),
	GRAY("Gray", "&7",
			"http://textures.minecraft.net/texture/2a17e97037ce353f85f5c65df435d29449a88da4442e4361cf99abbe1f892fb"),
	AQUA("Aqua", "&b",
			"http://textures.minecraft.net/texture/4c374acea78efbefa798be1b27e9714c36411e202eecd37b8cfcfd249a862e"),
	WHITE("White", "&f",
			"http://textures.minecraft.net/texture/ad93117b9e180e0dc39e5e8a0508482cf1f60e446e022978fe0651a562a597f"),
	RAINBOW("&cR&6a&ei&an&2b&bo&1w", "",
			"http://textures.minecraft.net/texture/a43efd7aed6b697d601ac7738661af68dd57518ec97b31e9c99d56b6862bd0c1");

	private final String name;

	private final String color;

	private final String url;

	HeadColors(String name, String color, String url) {
		this.name = name;
		this.color = color;
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public String getColor() {
		return color;
	}

	public String getUrl() {
		return this.url;
	}
}
