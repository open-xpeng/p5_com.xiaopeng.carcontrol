package org.apache.commons.compress.compressors.bzip2;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.room.RoomDatabase;
import com.google.zxing.pdf417.PDF417Common;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.lib.framework.moduleinterface.carcontroller.IInputController;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.speech.protocol.node.navi.bean.NaviPreferenceBean;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.archivers.zip.UnixStat;

/* loaded from: classes3.dex */
final class Rand {
    private static final int[] RNUMS = {619, 720, 127, 481, 931, 816, 813, 233, 566, 247, 985, 724, NaviPreferenceBean.PATH_PREF_AVOID_FERRIES, 454, 863, 491, 741, 242, 949, 214, 733, 859, 335, 708, 621, 574, 73, 654, 730, 472, 419, 436, 278, 496, 867, NaviPreferenceBean.PATH_PREF_UNPAVED, 399, 680, 480, 51, 878, 465, 811, 169, 869, 675, TypedValues.Motion.TYPE_QUANTIZE_INTERPOLATOR_TYPE, 697, 867, 561, 862, 687, 507, 283, 482, 129, 807, 591, 733, 623, IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE, 238, 59, 379, 684, 877, 625, 169, 643, 105, 170, TypedValues.Motion.TYPE_PATHMOTION_ARC, IInputController.KEYCODE_KNOB_VOL_UP, 932, 727, 476, 693, TypedValues.Cycle.TYPE_WAVE_PHASE, 174, 647, 73, 122, 335, 530, 442, 853, 695, 249, 445, 515, 909, 545, 703, 919, 874, 474, 882, 500, 594, TypedValues.Motion.TYPE_QUANTIZE_INTERPOLATOR_ID, 641, 801, 220, 162, 819, 984, 589, 513, 495, 799, 161, TypedValues.Motion.TYPE_QUANTIZE_INTERPOLATOR, 958, 533, 221, AssembleRequest.ASSEMBLE_ACTION_CANCEL, 386, 867, 600, 782, 382, 596, 414, 171, IInputController.KEYCODE_LEFT_OK_BUTTON, 375, 682, 485, 911, 276, 98, 553, 163, 354, 666, 933, TypedValues.Cycle.TYPE_WAVE_OFFSET, 341, 533, 870, 227, 730, 475, 186, TarConstants.VERSION_OFFSET, 647, 537, 686, 600, 224, 469, 68, 770, 919, 190, 373, 294, 822, 808, NaviPreferenceBean.PATH_PREF_CARPOOL, 184, 943, 795, 384, 383, 461, 404, 758, 839, 887, 715, 67, 618, 276, NaviPreferenceBean.PATH_PREF_FERRIES, 918, 873, 777, TypedValues.Motion.TYPE_QUANTIZE_INTERPOLATOR, 560, 951, SpeechConstant.SoundLocation.MAX_ANGLE, 578, 722, 79, 804, 96, 409, 713, 940, 652, 934, 970, 447, TypedValues.Attributes.TYPE_PIVOT_TARGET, 353, 859, 672, 112, 785, 645, 863, 803, 350, 139, 93, 354, 99, 820, 908, TypedValues.Motion.TYPE_POLAR_RELATIVETO, 772, 154, 274, 580, 184, 79, 626, 630, 742, 653, 282, 762, 623, 680, 81, 927, 626, 789, 125, 411, IInputController.KEYCODE_KNOB_VOL_DOWN, 938, 300, 821, 78, 343, 175, 128, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 170, 774, 972, 275, RoomDatabase.MAX_BIND_PARAMETER_CNT, 639, 495, 78, 352, 126, 857, 956, 358, 619, 580, 124, 737, 594, TypedValues.Transition.TYPE_FROM, TypedValues.Motion.TYPE_QUANTIZE_INTERPOLATOR_ID, 669, 112, 134, 694, 363, 992, 809, 743, 168, 974, 944, 375, 748, 52, 600, 747, 642, 182, 862, 81, 344, 805, 988, 739, 511, 655, 814, 334, 249, 515, 897, 955, 664, 981, 649, 113, 974, 459, 893, 228, 433, 837, 553, 268, 926, 240, 102, 654, 459, 51, 686, 754, 806, 760, UnixStat.DEFAULT_DIR_PERM, TypedValues.Cycle.TYPE_ALPHA, 415, 394, 687, TypedValues.Transition.TYPE_DURATION, 946, 670, 656, TypedValues.Motion.TYPE_QUANTIZE_MOTIONSTEPS, 738, 392, 760, 799, 887, 653, 978, 321, 576, 617, 626, 502, 894, 679, 243, 440, 680, 879, 194, 572, 640, 724, 926, 56, NaviPreferenceBean.PATH_PREF_FERRIES, TypedValues.Transition.TYPE_DURATION, TypedValues.Transition.TYPE_TRANSITION_FLAGS, 151, 457, 449, 797, 195, 791, 558, 945, 679, 297, 59, 87, 824, 713, 663, 412, 693, 342, TypedValues.Motion.TYPE_ANIMATE_CIRCLEANGLE_TO, 134, 108, 571, 364, 631, NaviPreferenceBean.PATH_PREF_COUNTRY_BORDER, 174, 643, 304, 329, 343, 97, 430, 751, 497, 314, 983, 374, 822, PDF417Common.MAX_CODEWORDS_IN_BARCODE, 140, NaviPreferenceBean.PATH_PREF_CARPOOL, 73, TarConstants.VERSION_OFFSET, 980, 736, 876, 478, 430, 305, 170, 514, 364, 692, 829, 82, 855, 953, 676, 246, 369, 970, 294, 750, 807, 827, IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE, 790, 288, 923, 804, 378, 215, 828, 592, 281, 565, 555, 710, 82, 896, 831, 547, 261, IInputController.KEYCODE_WIND_EXIT_MODE, 462, 293, 465, 502, 56, 661, 821, 976, 991, 658, 869, TypedValues.Custom.TYPE_DIMENSION, 758, 745, 193, 768, 550, TypedValues.Motion.TYPE_DRAW_PATH, 933, 378, 286, 215, 979, 792, 961, 61, 688, 793, 644, 986, TypedValues.Cycle.TYPE_ALPHA, 106, 366, TypedValues.Custom.TYPE_DIMENSION, 644, 372, 567, 466, 434, 645, NaviPreferenceBean.PATH_PREF_UNPAVED, 389, 550, 919, 135, 780, 773, 635, 389, TypedValues.Transition.TYPE_TRANSITION_FLAGS, 100, 626, 958, 165, 504, 920, 176, 193, 713, 857, 265, NaviPreferenceBean.PATH_PREF_AVOID_TUNNEL, 50, 668, 108, 645, 990, 626, 197, 510, 357, 358, 850, 858, 364, 936, 638};

    Rand() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int rNums(int i) {
        return RNUMS[i];
    }
}
