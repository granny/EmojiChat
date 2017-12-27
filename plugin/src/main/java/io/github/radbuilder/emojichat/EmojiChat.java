package io.github.radbuilder.emojichat;

import com.google.common.io.BaseEncoding;
import io.github.radbuilder.emojichat.hooks.DiscordSrvHook;
import io.github.radbuilder.emojichat.hooks.EmojiChatHook;
import io.github.radbuilder.emojichat.hooks.MVdWPlaceholderApiHook;
import io.github.radbuilder.emojichat.hooks.PlaceholderApiHook;
import io.github.radbuilder.emojichat.metrics.MetricsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * EmojiChat main class.
 *
 * @author RadBuilder
 * @since 1.0
 */
public class EmojiChat extends JavaPlugin {
	/**
	 * Stores the shortcuts and the emojis with the shortcut as the key and emoji as the value.
	 */
	HashMap<String, String> emojiMap;
	/**
	 * The EmojiChat GUI.
	 */
	EmojiChatGui emojiChatGui;
	/**
	 * The EmojiChat update checker.
	 */
	EmojiChatUpdateChecker updateChecker;
	/**
	 * The ResourcePack URL.
	 */
	final String PACK_URL = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.3/EmojiChat.ResourcePack.v1.3.zip";
	/**
	 * The SHA1 sum of the ResourcePack as a byte array.
	 */
	byte[] PACK_SHA1;
	/**
	 * List of enabled EmojiChat hooks.
	 */
	private List<EmojiChatHook> enabledHooks;
	/**
	 * The metrics data handler.
	 */
	private MetricsHandler metricsHandler;
	
	@Override
	public void onEnable() {
		emojiMap = new HashMap<>();
		enabledHooks = new ArrayList<>();
		emojiChatGui = new EmojiChatGui(this);
		updateChecker = new EmojiChatUpdateChecker(this);
		
		loadList(); // Load the emoji list
		
		loadHooks(); // Load plugin hooks
		
		metricsHandler = new MetricsHandler(this); // Creates the metrics handler for metrics gathering
		
		PACK_SHA1 = BaseEncoding.base16().lowerCase().decode("446369bae955920c20c6c9441cb1f47f89338c19"); // Allows applying a cached version of the ResourcePack if available
		
		// Register the chat listener
		Bukkit.getPluginManager().registerEvents(new EmojiChatListener(this), this);
		
		// Register the "emojichat" and "ec" commands
		EmojiChatCommand emojiChatCommand = new EmojiChatCommand(this);
		getCommand("emojichat").setExecutor(emojiChatCommand);
		getCommand("ec").setExecutor(emojiChatCommand);
		
		// Check for updates
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			updateChecker.checkForUpdates();
			if (updateChecker.updateAvailable) {
				getLogger().info("An update for EmojiChat is available.");
				getLogger().info("Current version: " + updateChecker.currentVersion + ". Latest version: " + updateChecker.latestVersion + ".");
			}
		});
	}
	
	@Override
	public void onDisable() {
		for (EmojiChatHook hook : enabledHooks) { // Disables enabled hooks, if any
			hook.disable();
		}
		enabledHooks.clear();
		emojiMap.clear();
	}
	
	/**
	 * Hooks into available plugins.
	 */
	private void loadHooks() {
		if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) { // Hook DiscordSRV if installed
			enabledHooks.add(new DiscordSrvHook(this));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) { // Hook MVdWPlaceholderAPI if installed
			enabledHooks.add(new MVdWPlaceholderApiHook(this));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { // Hook PlaceholderAPI if installed
			enabledHooks.add(new PlaceholderApiHook(this));
		}
	}
	
	/**
	 * Gets the {@link #emojiMap}.
	 *
	 * @return The {@link #emojiMap}.
	 */
	public HashMap<String, String> getEmojiMap() {
		return emojiMap;
	}
	
	/**
	 * Gets the list of enabled {@link io.github.radbuilder.emojichat.hooks.EmojiChatHook}.
	 *
	 * @return The list of enabled {@link io.github.radbuilder.emojichat.hooks.EmojiChatHook}.
	 */
	public List<EmojiChatHook> getEnabledHooks() {
		return enabledHooks;
	}
	
	/**
	 * Gets the {@link #metricsHandler}.
	 *
	 * @return The {@link #metricsHandler}.
	 */
	public MetricsHandler getMetricsHandler() {
		return metricsHandler;
	}
	
	/**
	 * Loads the emojis and their shortcuts into the {@link #emojiMap}.
	 */
	private void loadList() {
		emojiMap.put(":100:", "가");
		emojiMap.put(":1234:", "각");
		emojiMap.put(":grinning:", "갂");
		emojiMap.put(":grimacing:", "갃");
		emojiMap.put(":grin:", "간");
		emojiMap.put(":joy:", "갅");
		emojiMap.put(":rofl:", "갆");
		emojiMap.put(":smiley:", "갇");
		emojiMap.put(":smile:", "갈");
		emojiMap.put(":sweat_smile:", "갉");
		emojiMap.put(":laughing:", "갊");
		emojiMap.put(":innocent:", "갋");
		emojiMap.put(":wink:", "갌");
		emojiMap.put(":blush:", "갍");
		emojiMap.put(":slightly_smiling_face:", "갎");
		emojiMap.put(":upside_down_face:", "갏");
		emojiMap.put(":relaxed:", "감");
		emojiMap.put(":yum:", "갑");
		emojiMap.put(":relieved:", "값");
		emojiMap.put(":heart_eyes:", "갓");
		emojiMap.put(":kissing_heart:", "갔");
		emojiMap.put(":kissing:", "강");
		emojiMap.put(":kissing_smiling_eyes:", "갖");
		emojiMap.put(":kissing_closed_eyes:", "갗");
		emojiMap.put(":stuck_out_tongue_winking_eye:", "갘");
		emojiMap.put(":stuck_out_tongue_closed_eyes:", "같");
		emojiMap.put(":stuck_out_tongue:", "갚");
		emojiMap.put(":money_mouth_face:", "갛");
		emojiMap.put(":nerd_face:", "개");
		emojiMap.put(":sunglasses:", "객");
		emojiMap.put(":clown_face:", "갞");
		emojiMap.put(":cowboy_hat_face:", "갟");
		emojiMap.put(":hugs:", "갠");
		emojiMap.put(":smirk:", "갡");
		emojiMap.put(":no_mouth:", "갢");
		emojiMap.put(":neutral_face:", "갣");
		emojiMap.put(":expressionless:", "갤");
		emojiMap.put(":unamused:", "갥");
		emojiMap.put(":roll_eyes:", "갦");
		emojiMap.put(":thinking:", "갧");
		emojiMap.put(":lying_face:", "갨");
		emojiMap.put(":flushed:", "갩");
		emojiMap.put(":disappointed:", "갪");
		emojiMap.put(":worried:", "갫");
		emojiMap.put(":angry:", "갬");
		emojiMap.put(":rage:", "갭");
		emojiMap.put(":pensive:", "갮");
		emojiMap.put(":confused:", "갯");
		emojiMap.put(":slightly_frowning_face:", "갰");
		emojiMap.put(":frowning_face:", "갱");
		emojiMap.put(":persevere:", "갲");
		emojiMap.put(":confounded:", "갳");
		emojiMap.put(":tired_face:", "갴");
		emojiMap.put(":weary:", "갵");
		emojiMap.put(":triumph:", "갶");
		emojiMap.put(":open_mouth:", "갷");
		emojiMap.put(":scream:", "갸");
		emojiMap.put(":fearful:", "갹");
		emojiMap.put(":cold_sweat:", "갺");
		emojiMap.put(":hushed:", "갻");
		emojiMap.put(":frowning:", "갼");
		emojiMap.put(":anguished:", "갽");
		emojiMap.put(":cry:", "갾");
		emojiMap.put(":disappointed_relieved:", "갿");
		emojiMap.put(":drooling_face:", "걀");
		emojiMap.put(":sleepy:", "걁");
		emojiMap.put(":sweat:", "걂");
		emojiMap.put(":sob:", "걃");
		emojiMap.put(":dizzy_face:", "걄");
		emojiMap.put(":astonished:", "걅");
		emojiMap.put(":zipper_mouth_face:", "걆");
		emojiMap.put(":nauseated_face:", "걇");
		emojiMap.put(":sneezing_face:", "걈");
		emojiMap.put(":mask:", "걉");
		emojiMap.put(":face_with_thermometer:", "걊");
		emojiMap.put(":face_with_head_bandage:", "걋");
		emojiMap.put(":sleeping:", "걌");
		emojiMap.put(":zzz:", "걍");
		emojiMap.put(":poop:", "걎");
		emojiMap.put(":smiling_imp:", "걏");
		emojiMap.put(":imp:", "걐");
		emojiMap.put(":japanese_ogre:", "걑");
		emojiMap.put(":japanese_goblin:", "걒");
		emojiMap.put(":skull:", "걓");
		emojiMap.put(":ghost:", "걔");
		emojiMap.put(":alien:", "걕");
		emojiMap.put(":robot:", "걖");
		emojiMap.put(":smiley_cat:", "걗");
		emojiMap.put(":smile_cat:", "걘");
		emojiMap.put(":joy_cat:", "걙");
		emojiMap.put(":heart_eyes_cat:", "걚");
		emojiMap.put(":smirk_cat:", "걛");
		emojiMap.put(":kissing_cat:", "걜");
		emojiMap.put(":scream_cat:", "걝");
		emojiMap.put(":crying_cat_face:", "걞");
		emojiMap.put(":pouting_cat:", "걟");
		emojiMap.put(":raised_hands:", "걠");
		emojiMap.put(":clap:", "걡");
		emojiMap.put(":wave:", "걢");
		emojiMap.put(":call_me_hand:", "걣");
		emojiMap.put(":+1:", "걤");
		emojiMap.put(":-1:", "걥");
		emojiMap.put(":facepunch:", "걦");
		emojiMap.put(":fist:", "걧");
		emojiMap.put(":fist_left:", "걨");
		emojiMap.put(":fist_right:", "걩");
		emojiMap.put(":v:", "걪");
		emojiMap.put(":ok_hand:", "걫");
		emojiMap.put(":raised_hand:", "걬");
		emojiMap.put(":raised_back_of_hand:", "걭");
		emojiMap.put(":open_hands:", "걮");
		emojiMap.put(":muscle:", "걯");
		emojiMap.put(":pray:", "거");
		emojiMap.put(":handshake:", "걱");
		emojiMap.put(":point_up:", "걲");
		emojiMap.put(":point_up_2:", "걳");
		emojiMap.put(":point_down:", "건");
		emojiMap.put(":point_left:", "걵");
		emojiMap.put(":point_right:", "걶");
		// emojiMap.put(":fu:", "걷");
		emojiMap.put(":raised_hand_with_fingers_splayed:", "걸");
		emojiMap.put(":metal:", "걹");
		emojiMap.put(":crossed_fingers:", "걺");
		emojiMap.put(":vulcan_salute:", "걻");
		emojiMap.put(":writing_hand:", "걼");
		emojiMap.put(":selfie:", "걽");
		// emojiMap.put(":lips:", "걾");
		// emojiMap.put(":tongue:", "걿");
		emojiMap.put(":ear:", "검");
		emojiMap.put(":nose:", "겁");
		emojiMap.put(":eye:", "겂");
		emojiMap.put(":eyes:", "것");
		emojiMap.put(":womans_clothes:", "겄");
		emojiMap.put(":tshirt:", "겅");
		emojiMap.put(":jeans:", "겆");
		emojiMap.put(":necktie:", "겇");
		emojiMap.put(":dress:", "겈");
		// emojiMap.put(":bikini:", "겉");
		emojiMap.put(":kimono:", "겊");
		emojiMap.put(":lipstick:", "겋");
		// emojiMap.put(":kiss:", "게");
		emojiMap.put(":footprints:", "겍");
		emojiMap.put(":high_heel:", "겎");
		emojiMap.put(":sandal:", "겏");
		emojiMap.put(":boot:", "겐");
		emojiMap.put(":mans_shoe:", "겑");
		emojiMap.put(":athletic_shoe:", "겒");
		emojiMap.put(":womans_hat:", "겓");
		emojiMap.put(":tophat:", "겔");
		emojiMap.put(":rescue_worker_helmet:", "겕");
		emojiMap.put(":mortar_board:", "겖");
		emojiMap.put(":crown:", "겗");
		emojiMap.put(":school_satchel:", "겘");
		emojiMap.put(":pouch:", "겙");
		emojiMap.put(":purse:", "겚");
		emojiMap.put(":handbag:", "겛");
		emojiMap.put(":briefcase:", "겜");
		emojiMap.put(":eyeglasses:", "겝");
		emojiMap.put(":dark_sunglasses:", "겞");
		emojiMap.put(":ring:", "겟");
		emojiMap.put(":closed_umbrella:", "겠");
		emojiMap.put(":dog:", "겡");
		emojiMap.put(":cat:", "겢");
		emojiMap.put(":mouse:", "겣");
		emojiMap.put(":hamster:", "겤");
		emojiMap.put(":rabbit:", "겥");
		emojiMap.put(":fox_face:", "겦");
		emojiMap.put(":bear:", "겧");
		emojiMap.put(":panda_face:", "겨");
		emojiMap.put(":koala:", "격");
		emojiMap.put(":tiger:", "겪");
		emojiMap.put(":lion:", "겫");
		emojiMap.put(":cow:", "견");
		emojiMap.put(":pig:", "겭");
		emojiMap.put(":pig_nose:", "겮");
		emojiMap.put(":frog:", "겯");
		emojiMap.put(":squid:", "결");
		emojiMap.put(":octopus:", "겱");
		emojiMap.put(":shrimp:", "겲");
		emojiMap.put(":monkey_face:", "겳");
		emojiMap.put(":gorilla:", "겴");
		emojiMap.put(":see_no_evil:", "겵");
		emojiMap.put(":hear_no_evil:", "겶");
		emojiMap.put(":speak_no_evil:", "겷");
		emojiMap.put(":monkey:", "겸");
		emojiMap.put(":chicken:", "겹");
		emojiMap.put(":penguin:", "겺");
		emojiMap.put(":bird:", "겻");
		emojiMap.put(":baby_chick:", "겼");
		emojiMap.put(":hatching_chick:", "경");
		emojiMap.put(":hatched_chick:", "겾");
		emojiMap.put(":duck:", "겿");
		emojiMap.put(":eagle:", "곀");
		emojiMap.put(":owl:", "곁");
		emojiMap.put(":bat:", "곂");
		emojiMap.put(":wolf:", "곃");
		emojiMap.put(":boar:", "계");
		emojiMap.put(":horse:", "곅");
		emojiMap.put(":unicorn:", "곆");
		emojiMap.put(":honeybee:", "곇");
		emojiMap.put(":bug:", "곈");
		emojiMap.put(":butterfly:", "곉");
		emojiMap.put(":snail:", "곊");
		emojiMap.put(":beetle:", "곋");
		emojiMap.put(":ant:", "곌");
		emojiMap.put(":spider:", "곍");
		emojiMap.put(":scorpion:", "곎");
		emojiMap.put(":crab:", "곏");
		emojiMap.put(":snake:", "곐");
		emojiMap.put(":lizard:", "곑");
		emojiMap.put(":turtle:", "곒");
		emojiMap.put(":tropical_fish:", "곓");
		emojiMap.put(":fish:", "곔");
		emojiMap.put(":blowfish:", "곕");
		emojiMap.put(":dolphin:", "곖");
		emojiMap.put(":shark:", "곗");
		emojiMap.put(":whale:", "곘");
		emojiMap.put(":whale2:", "곙");
		emojiMap.put(":crocodile:", "곚");
		emojiMap.put(":leopard:", "곛");
		emojiMap.put(":tiger2:", "곜");
		emojiMap.put(":water_buffalo:", "곝");
		emojiMap.put(":ox:", "곞");
		emojiMap.put(":cow2:", "곟");
		emojiMap.put(":deer:", "고");
		emojiMap.put(":dromedary_camel:", "곡");
		emojiMap.put(":camel:", "곢");
		emojiMap.put(":elephant:", "곣");
		emojiMap.put(":rhinoceros:", "곤");
		emojiMap.put(":goat:", "곥");
		emojiMap.put(":ram:", "곦");
		emojiMap.put(":sheep:", "곧");
		emojiMap.put(":racehorse:", "골");
		emojiMap.put(":pig2:", "곩");
		emojiMap.put(":rat:", "곪");
		emojiMap.put(":mouse2:", "곫");
		emojiMap.put(":rooster:", "곬");
		emojiMap.put(":turkey:", "곭");
		emojiMap.put(":dove:", "곮");
		emojiMap.put(":dog2:", "곯");
		emojiMap.put(":poodle:", "곰");
		emojiMap.put(":cat2:", "곱");
		emojiMap.put(":rabbit2:", "곲");
		emojiMap.put(":chipmunk:", "곳");
		emojiMap.put(":paw_prints:", "곴");
		emojiMap.put(":dragon:", "공");
		emojiMap.put(":dragon_face:", "곶");
		emojiMap.put(":cactus:", "곷");
		emojiMap.put(":christmas_tree:", "곸");
		emojiMap.put(":evergreen_tree:", "곹");
		emojiMap.put(":deciduous_tree:", "곺");
		emojiMap.put(":palm_tree:", "곻");
		emojiMap.put(":seedling:", "과");
		emojiMap.put(":herb:", "곽");
		emojiMap.put(":shamrock:", "곾");
		emojiMap.put(":four_leaf_clover:", "곿");
		emojiMap.put(":bamboo:", "관");
		emojiMap.put(":tanabata_tree:", "괁");
		emojiMap.put(":leaves:", "괂");
		emojiMap.put(":fallen_leaf:", "괃");
		emojiMap.put(":maple_leaf:", "괄");
		emojiMap.put(":ear_of_rice:", "괅");
		emojiMap.put(":hibiscus:", "괆");
		emojiMap.put(":sunflower:", "괇");
		emojiMap.put(":rose:", "괈");
		emojiMap.put(":wilted_flower:", "괉");
		emojiMap.put(":tulip:", "괊");
		emojiMap.put(":blossom:", "괋");
		emojiMap.put(":cherry_blossom:", "괌");
		emojiMap.put(":bouquet:", "괍");
		emojiMap.put(":mushroom:", "괎");
		emojiMap.put(":chestnut:", "괏");
		emojiMap.put(":jack_o_lantern:", "괐");
		emojiMap.put(":shell:", "광");
		emojiMap.put(":spider_web:", "괒");
		emojiMap.put(":earth_americas:", "괓");
		emojiMap.put(":earth_africa:", "괔");
		emojiMap.put(":earth_asia:", "괕");
		emojiMap.put(":full_moon:", "괖");
		emojiMap.put(":waning_gibbous_moon:", "괗");
		emojiMap.put(":last_quarter_moon:", "괘");
		emojiMap.put(":waning_crescent_moon:", "괙");
		emojiMap.put(":new_moon:", "괚");
		emojiMap.put(":waxing_crescent_moon:", "괛");
		emojiMap.put(":first_quarter_moon:", "괜");
		emojiMap.put(":waxing_gibbous_moon:", "괝");
		emojiMap.put(":new_moon_with_face:", "괞");
		emojiMap.put(":full_moon_with_face:", "괟");
		emojiMap.put(":first_quarter_moon_with_face:", "괠");
		emojiMap.put(":last_quarter_moon_with_face:", "괡");
		emojiMap.put(":sun_with_face:", "괢");
		emojiMap.put(":crescent_moon:", "괣");
		emojiMap.put(":star:", "괤");
		emojiMap.put(":star2:", "괥");
		emojiMap.put(":dizzy:", "괦");
		emojiMap.put(":sparkles:", "괧");
		emojiMap.put(":comet:", "괨");
		emojiMap.put(":sunny:", "괩");
		emojiMap.put(":sun_behind_small_cloud:", "괪");
		emojiMap.put(":partly_sunny:", "괫");
		emojiMap.put(":sun_behind_large_cloud:", "괬");
		emojiMap.put(":sun_behind_rain_cloud:", "괭");
		emojiMap.put(":cloud:", "괮");
		emojiMap.put(":cloud_with_rain:", "괯");
		emojiMap.put(":cloud_with_lightning_and_rain:", "괰");
		emojiMap.put(":cloud_with_lightning:", "괱");
		emojiMap.put(":zap:", "괲");
		emojiMap.put(":fire:", "괳");
		emojiMap.put(":boom:", "괴");
		emojiMap.put(":snowflake:", "괵");
		emojiMap.put(":cloud_with_snow:", "괶");
		emojiMap.put(":snowman:", "괷");
		emojiMap.put(":snowman_with_snow:", "괸");
		emojiMap.put(":wind_face:", "괹");
		emojiMap.put(":dash:", "괺");
		emojiMap.put(":tornado:", "괻");
		emojiMap.put(":fog:", "괼");
		emojiMap.put(":open_umbrella:", "괽");
		emojiMap.put(":umbrella:", "괾");
		emojiMap.put(":droplet:", "괿");
	}
}
