package com.xiaopeng.speech.protocol.node.carcontrol;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CarcontrolEvent;

/* loaded from: classes2.dex */
public class CarcontrolNode_Processor implements ICommandProcessor {
    private CarcontrolNode mTarget;

    public CarcontrolNode_Processor(CarcontrolNode carcontrolNode) {
        this.mTarget = carcontrolNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2120833774:
                if (str.equals(CarcontrolEvent.PSN_SEAT_MOVE_UP)) {
                    c = 0;
                    break;
                }
                break;
            case -2066594564:
                if (str.equals(CarcontrolEvent.WINDOW_PASSENGER_OPEN)) {
                    c = 1;
                    break;
                }
                break;
            case -2003475528:
                if (str.equals(CarcontrolEvent.SEAT_ADJUST)) {
                    c = 2;
                    break;
                }
                break;
            case -1932172590:
                if (str.equals(CarcontrolEvent.DIRECT_PORT_ON)) {
                    c = 3;
                    break;
                }
                break;
            case -1917375779:
                if (str.equals(CarcontrolEvent.MIRROR_REAR_CLOSE)) {
                    c = 4;
                    break;
                }
                break;
            case -1881291550:
                if (str.equals(CarcontrolEvent.LIGHT_POSITION_OFF)) {
                    c = 5;
                    break;
                }
                break;
            case -1839847196:
                if (str.equals(CarcontrolEvent.LEG_HIGHEST)) {
                    c = 6;
                    break;
                }
                break;
            case -1768222068:
                if (str.equals(CarcontrolEvent.WIPER_SPEED_DOWN)) {
                    c = 7;
                    break;
                }
                break;
            case -1721073980:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_OFF)) {
                    c = '\b';
                    break;
                }
                break;
            case -1711827085:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_ON)) {
                    c = '\t';
                    break;
                }
                break;
            case -1698181319:
                if (str.equals(CarcontrolEvent.LOW_VOLUME_ON)) {
                    c = '\n';
                    break;
                }
                break;
            case -1675857019:
                if (str.equals(CarcontrolEvent.TIRE_PRESSURE_SHOW)) {
                    c = 11;
                    break;
                }
                break;
            case -1671052557:
                if (str.equals(CarcontrolEvent.ENERGY_RECYCLE_LOW)) {
                    c = '\f';
                    break;
                }
                break;
            case -1653284995:
                if (str.equals(CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_ON)) {
                    c = '\r';
                    break;
                }
                break;
            case -1606824866:
                if (str.equals(CarcontrolEvent.CONTROL_CAPSULE_UNIVERSAL_SET)) {
                    c = 14;
                    break;
                }
                break;
            case -1606320269:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_FOREMOST)) {
                    c = 15;
                    break;
                }
                break;
            case -1527032229:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_OFF)) {
                    c = 16;
                    break;
                }
                break;
            case -1515588522:
                if (str.equals(CarcontrolEvent.CONTROL_SEAT_RESUME)) {
                    c = 17;
                    break;
                }
                break;
            case -1513394514:
                if (str.equals(CarcontrolEvent.PSN_SEAT_MOVE_FORWARD)) {
                    c = 18;
                    break;
                }
                break;
            case -1494382542:
                if (str.equals(CarcontrolEvent.SEAT_BACKREST_FORWARD)) {
                    c = 19;
                    break;
                }
                break;
            case -1463564858:
                if (str.equals(CarcontrolEvent.LIGHT_AUTO_ON)) {
                    c = 20;
                    break;
                }
                break;
            case -1458343142:
                if (str.equals(CarcontrolEvent.MIRROR_REAR_ON)) {
                    c = 21;
                    break;
                }
                break;
            case -1430827874:
                if (str.equals(CarcontrolEvent.MIST_LIGHT_ON)) {
                    c = 22;
                    break;
                }
                break;
            case -1407167676:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_UP)) {
                    c = 23;
                    break;
                }
                break;
            case -1405991280:
                if (str.equals(CarcontrolEvent.MIST_LIGHT_OFF)) {
                    c = 24;
                    break;
                }
                break;
            case -1375267742:
                if (str.equals(CarcontrolEvent.LEG_DOWN)) {
                    c = 25;
                    break;
                }
                break;
            case -1281862925:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_OFF)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1178928744:
                if (str.equals(CarcontrolEvent.WIPER_MEDIUM)) {
                    c = 27;
                    break;
                }
                break;
            case -1104013483:
                if (str.equals(CarcontrolEvent.LOW_VOLUME_OFF)) {
                    c = 28;
                    break;
                }
                break;
            case -1026240870:
                if (str.equals(CarcontrolEvent.SEAT_BACKREST_BACK)) {
                    c = 29;
                    break;
                }
                break;
            case -1025760425:
                if (str.equals(CarcontrolEvent.SEAT_BACKREST_REAR)) {
                    c = 30;
                    break;
                }
                break;
            case -968493410:
                if (str.equals(CarcontrolEvent.WINDOW_RIGHT_REAR_CLOSE)) {
                    c = 31;
                    break;
                }
                break;
            case -872634405:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_ON)) {
                    c = ' ';
                    break;
                }
                break;
            case -792915409:
                if (str.equals(CarcontrolEvent.WINDOW_LEFT_CLOSE)) {
                    c = '!';
                    break;
                }
                break;
            case -678703178:
                if (str.equals(CarcontrolEvent.WINDOW_REAR_OPEN)) {
                    c = '\"';
                    break;
                }
                break;
            case -672533029:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MAX)) {
                    c = '#';
                    break;
                }
                break;
            case -672532791:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MIN)) {
                    c = '$';
                    break;
                }
                break;
            case -672527143:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_SET)) {
                    c = '%';
                    break;
                }
                break;
            case -614876148:
                if (str.equals(CarcontrolEvent.LIGHT_POSITION_ON)) {
                    c = '&';
                    break;
                }
                break;
            case -509776894:
                if (str.equals(CarcontrolEvent.MODES_DRIVING_CONSERVATION)) {
                    c = '\'';
                    break;
                }
                break;
            case -466933999:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_FORWARD)) {
                    c = '(';
                    break;
                }
                break;
            case -461837539:
                if (str.equals(CarcontrolEvent.LIGHT_LOW_OFF)) {
                    c = ')';
                    break;
                }
                break;
            case -390627019:
                if (str.equals(CarcontrolEvent.PSN_SEAT_BACKREST_FORWARD)) {
                    c = '*';
                    break;
                }
                break;
            case -388893173:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_PAUSE)) {
                    c = '+';
                    break;
                }
                break;
            case -310219003:
                if (str.equals(CarcontrolEvent.WIPER_SPEED_UP)) {
                    c = ',';
                    break;
                }
                break;
            case -308548080:
                if (str.equals(CarcontrolEvent.WINDOW_DRIVER_CLOSE)) {
                    c = '-';
                    break;
                }
                break;
            case -304980732:
                if (str.equals(CarcontrolEvent.ALTERNATING_PORT_OFF)) {
                    c = '.';
                    break;
                }
                break;
            case -284285602:
                if (str.equals(CarcontrolEvent.TRUNK_CLOSE)) {
                    c = '/';
                    break;
                }
                break;
            case -263259933:
                if (str.equals(CarcontrolEvent.ENERGY_RECYCLE_DOWN)) {
                    c = '0';
                    break;
                }
                break;
            case -263147037:
                if (str.equals(CarcontrolEvent.ENERGY_RECYCLE_HIGH)) {
                    c = '1';
                    break;
                }
                break;
            case -67611537:
                if (str.equals(CarcontrolEvent.MODES_DRIVING_SPORT)) {
                    c = '2';
                    break;
                }
                break;
            case -50794088:
                if (str.equals(CarcontrolEvent.LIGHT_HOME_OFF)) {
                    c = '3';
                    break;
                }
                break;
            case 128955186:
                if (str.equals(CarcontrolEvent.WINDOW_DRIVER_OPEN)) {
                    c = '4';
                    break;
                }
                break;
            case 169414246:
                if (str.equals(CarcontrolEvent.MODES_STEERING_NORMAL)) {
                    c = '5';
                    break;
                }
                break;
            case 232191708:
                if (str.equals(CarcontrolEvent.DIRECT_PORT_OFF)) {
                    c = '6';
                    break;
                }
                break;
            case 244379227:
                if (str.equals(CarcontrolEvent.LEG_UP)) {
                    c = '7';
                    break;
                }
                break;
            case 261368709:
                if (str.equals(CarcontrolEvent.SEAT_RESTORE)) {
                    c = '8';
                    break;
                }
                break;
            case 275456150:
                if (str.equals(CarcontrolEvent.LIGHT_HOME_ON)) {
                    c = '9';
                    break;
                }
                break;
            case 284513946:
                if (str.equals(CarcontrolEvent.WIPER_SUPERHIGH)) {
                    c = ':';
                    break;
                }
                break;
            case 287772561:
                if (str.equals(CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_OFF)) {
                    c = ';';
                    break;
                }
                break;
            case 294159518:
                if (str.equals(CarcontrolEvent.TRUNK_UNLOCK)) {
                    c = '<';
                    break;
                }
                break;
            case 342341845:
                if (str.equals(CarcontrolEvent.WINDOW_FRONT_CLOSE)) {
                    c = '=';
                    break;
                }
                break;
            case 348886406:
                if (str.equals(CarcontrolEvent.WINDOW_PASSENGER_CLOSE)) {
                    c = '>';
                    break;
                }
                break;
            case 404106359:
                if (str.equals(CarcontrolEvent.PSN_SEAT_BACKREST_BACK)) {
                    c = '?';
                    break;
                }
                break;
            case 407370447:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_UP)) {
                    c = '@';
                    break;
                }
                break;
            case 423846412:
                if (str.equals(CarcontrolEvent.WINDOW_REAR_CLOSE)) {
                    c = 'A';
                    break;
                }
                break;
            case 439725323:
                if (str.equals(CarcontrolEvent.WINDOW_LEFT_REAR_OPEN)) {
                    c = 'B';
                    break;
                }
                break;
            case 561507053:
                if (str.equals(CarcontrolEvent.WINDOWS_CLOSE)) {
                    c = 'C';
                    break;
                }
                break;
            case 572663477:
                if (str.equals(CarcontrolEvent.WINDOWS_OPEN)) {
                    c = 'D';
                    break;
                }
                break;
            case 579058598:
                if (str.equals(CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_OPEN)) {
                    c = 'E';
                    break;
                }
                break;
            case 626057995:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_DOWN)) {
                    c = 'F';
                    break;
                }
                break;
            case 640398363:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_BACK)) {
                    c = 'G';
                    break;
                }
                break;
            case 640472022:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_DOWN)) {
                    c = 'H';
                    break;
                }
                break;
            case 640878808:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_REAR)) {
                    c = 'I';
                    break;
                }
                break;
            case 658134902:
                if (str.equals(CarcontrolEvent.ENERGY_RECYCLE_MEDIA)) {
                    c = 'J';
                    break;
                }
                break;
            case 731254135:
                if (str.equals(CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_OPEN)) {
                    c = 'K';
                    break;
                }
                break;
            case 735391575:
                if (str.equals(CarcontrolEvent.WINDOW_LEFT_REAR_CLOSE)) {
                    c = 'L';
                    break;
                }
                break;
            case 759755804:
                if (str.equals(CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_CLOSE)) {
                    c = 'M';
                    break;
                }
                break;
            case 775765482:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_ON)) {
                    c = 'N';
                    break;
                }
                break;
            case 786135674:
                if (str.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_PAUSE)) {
                    c = 'O';
                    break;
                }
                break;
            case 829912197:
                if (str.equals(CarcontrolEvent.WIPER_HIGH)) {
                    c = 'P';
                    break;
                }
                break;
            case 830243044:
                if (str.equals(CarcontrolEvent.WIPER_SLOW)) {
                    c = 'Q';
                    break;
                }
                break;
            case 902513266:
                if (str.equals(CarcontrolEvent.SEAT_BACKREST_FOREMOST)) {
                    c = 'R';
                    break;
                }
                break;
            case 1026004922:
                if (str.equals(CarcontrolEvent.WINDOW_RIGHT_OPEN)) {
                    c = 'S';
                    break;
                }
                break;
            case 1054474012:
                if (str.equals(CarcontrolEvent.ENERGY_RECYCLE_UP)) {
                    c = 'T';
                    break;
                }
                break;
            case 1125696752:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_HIGHEST)) {
                    c = 'U';
                    break;
                }
                break;
            case 1182850155:
                if (str.equals(CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_CLOSE)) {
                    c = 'V';
                    break;
                }
                break;
            case 1216045284:
                if (str.equals(CarcontrolEvent.WINDOW_RIGHT_REAR_OPEN)) {
                    c = 'W';
                    break;
                }
                break;
            case 1390038351:
                if (str.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_COLOR)) {
                    c = 'X';
                    break;
                }
                break;
            case 1439290046:
                if (str.equals(CarcontrolEvent.WINDOWS_VENTILATE_ON)) {
                    c = 'Y';
                    break;
                }
                break;
            case 1446655346:
                if (str.equals(CarcontrolEvent.LEG_LOWEST)) {
                    c = 'Z';
                    break;
                }
                break;
            case 1509122673:
                if (str.equals(CarcontrolEvent.LIGHT_LOW_ON)) {
                    c = '[';
                    break;
                }
                break;
            case 1514182570:
                if (str.equals(CarcontrolEvent.ALTERNATING_PORT_ON)) {
                    c = '\\';
                    break;
                }
                break;
            case 1542318054:
                if (str.equals(CarcontrolEvent.SEAT_MOVE_LOWEST)) {
                    c = ']';
                    break;
                }
                break;
            case 1653758500:
                if (str.equals(CarcontrolEvent.TRUNK_OPEN)) {
                    c = '^';
                    break;
                }
                break;
            case 1668318320:
                if (str.equals(CarcontrolEvent.WINDOWS_VENTILATE_OFF)) {
                    c = '_';
                    break;
                }
                break;
            case 1671611365:
                if (str.equals(CarcontrolEvent.CONTROL_LIGHT_RESUME)) {
                    c = '`';
                    break;
                }
                break;
            case 1730189960:
                if (str.equals(CarcontrolEvent.WINDOW_RIGHT_CLOSE)) {
                    c = 'a';
                    break;
                }
                break;
            case 1775898419:
                if (str.equals(CarcontrolEvent.WINDOW_LEFT_OPEN)) {
                    c = 'b';
                    break;
                }
                break;
            case 1851379043:
                if (str.equals(CarcontrolEvent.CONTROL_XPEDAL_OFF)) {
                    c = 'c';
                    break;
                }
                break;
            case 1860837227:
                if (str.equals(CarcontrolEvent.CONTROL_XPEDAL_ON)) {
                    c = 'd';
                    break;
                }
                break;
            case 1874129512:
                if (str.equals(CarcontrolEvent.LIGHT_AUTO_OFF)) {
                    c = 'e';
                    break;
                }
                break;
            case 1949772309:
                if (str.equals(CarcontrolEvent.MODES_STEERING_SPORT)) {
                    c = 'f';
                    break;
                }
                break;
            case 1987631518:
                if (str.equals(CarcontrolEvent.PSN_SEAT_MOVE_BACK)) {
                    c = 'g';
                    break;
                }
                break;
            case 1987705177:
                if (str.equals(CarcontrolEvent.PSN_SEAT_MOVE_DOWN)) {
                    c = 'h';
                    break;
                }
                break;
            case 2002557289:
                if (str.equals(CarcontrolEvent.MODES_STEERING_SOFT)) {
                    c = 'i';
                    break;
                }
                break;
            case 2036006535:
                if (str.equals(CarcontrolEvent.MIRROR_REAR_SET)) {
                    c = 'j';
                    break;
                }
                break;
            case 2055024460:
                if (str.equals(CarcontrolEvent.MODES_DRIVING_NORMAL)) {
                    c = 'k';
                    break;
                }
                break;
            case 2089614285:
                if (str.equals(CarcontrolEvent.WINDOW_FRONT_OPEN)) {
                    c = 'l';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onPsnSeatMoveUp();
                return;
            case 1:
                this.mTarget.onWindowPassengerOpen(str, str2);
                return;
            case 2:
                this.mTarget.onSeatAdjust(str, str2);
                return;
            case 3:
                this.mTarget.onRightChargePortOpen(str, str2);
                return;
            case 4:
                this.mTarget.onMirrorRearClose(str, str2);
                return;
            case 5:
                this.mTarget.onLightPositionOff(str, str2);
                return;
            case 6:
                this.mTarget.onLegHighest(str, str2);
                return;
            case 7:
                this.mTarget.onWiperSpeedDown(str, str2);
                return;
            case '\b':
                this.mTarget.onControlScissorLeftDoorOff();
                return;
            case '\t':
                this.mTarget.onLightAtmosphereOn(str, str2);
                return;
            case '\n':
                this.mTarget.onLowVolumeOn(str, str2);
                return;
            case 11:
                this.mTarget.onTirePressureShow(str, str2);
                return;
            case '\f':
                this.mTarget.onEnergyRecycleLow(str, str2);
                return;
            case '\r':
                this.mTarget.onControlLightLanguageOn(str, str2);
                return;
            case 14:
                this.mTarget.setCapsuleUniversal(str, str2);
                return;
            case 15:
                this.mTarget.onSeatMoveForemost(str, str2);
                return;
            case 16:
                this.mTarget.onLightAtmosphereOff(str, str2);
                return;
            case 17:
                this.mTarget.onControlSeatResume(str, str2);
                return;
            case 18:
                this.mTarget.onPsnSeatMoveForward();
                return;
            case 19:
                this.mTarget.onSeatBackrestForward(str, str2);
                return;
            case 20:
                this.mTarget.onLightAutoOn(str, str2);
                return;
            case 21:
                this.mTarget.onMirrorRearOn(str, str2);
                return;
            case 22:
                this.mTarget.onMistLightOn(str, str2);
                return;
            case 23:
                this.mTarget.onLightAtmosphereBrightnessUp();
                return;
            case 24:
                this.mTarget.onMistLightOff(str, str2);
                return;
            case 25:
                this.mTarget.onLegDown(str, str2);
                return;
            case 26:
                this.mTarget.onControlScissorRightDoorOff();
                return;
            case 27:
                this.mTarget.onWiperMedium(str, str2);
                return;
            case 28:
                this.mTarget.onLowVolumeOff(str, str2);
                return;
            case 29:
                this.mTarget.onSeatBackrestBack(str, str2);
                return;
            case 30:
                this.mTarget.onSeatBackrestRear(str, str2);
                return;
            case 31:
                this.mTarget.onWindowRightRearClose(str, str2);
                return;
            case ' ':
                this.mTarget.onControlScissorRightDoorOn();
                return;
            case '!':
                this.mTarget.onWindowsLeftClose(str, str2);
                return;
            case '\"':
                this.mTarget.onWindowRearOpen(str, str2);
                return;
            case '#':
                this.mTarget.onLightAtmosphereBrightnessMax();
                return;
            case '$':
                this.mTarget.onLightAtmosphereBrightnessMin();
                return;
            case '%':
                this.mTarget.onLightAtmosphereBrightnessSet(str, str2);
                return;
            case '&':
                this.mTarget.onLightPositionOn(str, str2);
                return;
            case '\'':
                this.mTarget.onModesDrivingConservation(str, str2);
                return;
            case '(':
                this.mTarget.onSeatMoveForward(str, str2);
                return;
            case ')':
                this.mTarget.onLightLowOff(str, str2);
                return;
            case '*':
                this.mTarget.onPsnSeatBackrestForward();
                return;
            case '+':
                this.mTarget.onControlScissorLeftDoorPause();
                return;
            case ',':
                this.mTarget.onWiperSpeedUp(str, str2);
                return;
            case '-':
                this.mTarget.onWindowDriverClose(str, str2);
                return;
            case '.':
                this.mTarget.onLeftChargePortClose(str, str2);
                return;
            case '/':
                this.mTarget.onTrunkClose(str, str2);
                return;
            case '0':
                this.mTarget.onEnergyRecycleDown(str, str2);
                return;
            case '1':
                this.mTarget.onEnergyRecycleHigh(str, str2);
                return;
            case '2':
                this.mTarget.onModesDrivingSport(str, str2);
                return;
            case '3':
                this.mTarget.onLightHomeOff(str, str2);
                return;
            case '4':
                this.mTarget.onWindowDriverOpen(str, str2);
                return;
            case '5':
                this.mTarget.onModesSteeringNormal(str, str2);
                return;
            case '6':
                this.mTarget.onRightChargePortClose(str, str2);
                return;
            case '7':
                this.mTarget.onLegUp(str, str2);
                return;
            case '8':
                this.mTarget.onSeatRestore(str, str2);
                return;
            case '9':
                this.mTarget.onLightHomeOn(str, str2);
                return;
            case ':':
                this.mTarget.onWiperSuperhigh(str, str2);
                return;
            case ';':
                this.mTarget.onControlLightLanguageOff(str, str2);
                return;
            case '<':
                this.mTarget.onTrunkUnlock(str, str2);
                return;
            case '=':
                this.mTarget.onWindowFrontClose(str, str2);
                return;
            case '>':
                this.mTarget.onWindowPassengerClose(str, str2);
                return;
            case '?':
                this.mTarget.onPsnSeatBackrestBack();
                return;
            case '@':
                this.mTarget.onSeatMoveUp(str, str2);
                return;
            case 'A':
                this.mTarget.onWindowRearClose(str, str2);
                return;
            case 'B':
                this.mTarget.onWindowLeftRearOpen(str, str2);
                return;
            case 'C':
                this.mTarget.onWindowsClose(str, str2);
                return;
            case 'D':
                this.mTarget.onWindowsOpen(str, str2);
                return;
            case 'E':
                this.mTarget.onControlComfortableDrivingModeOpen(str, str2);
                return;
            case 'F':
                this.mTarget.onLightAtmosphereBrightnessDown();
                return;
            case 'G':
                this.mTarget.onSeatMoveBack(str, str2);
                return;
            case 'H':
                this.mTarget.onSeatMoveDown(str, str2);
                return;
            case 'I':
                this.mTarget.onSeatMoveRear(str, str2);
                return;
            case 'J':
                this.mTarget.onEnergyRecycleMedia(str, str2);
                return;
            case 'K':
                this.mTarget.onControlElectricCurtainOpen(str, str2);
                return;
            case 'L':
                this.mTarget.onWindowLeftRearClose(str, str2);
                return;
            case 'M':
                this.mTarget.onControlComfortableDrivingModeClose(str, str2);
                return;
            case 'N':
                this.mTarget.onControlScissorLeftDoorOn();
                return;
            case 'O':
                this.mTarget.onControlScissorRightDoorPause();
                return;
            case 'P':
                this.mTarget.onWiperHigh(str, str2);
                return;
            case 'Q':
                this.mTarget.onWiperSlow(str, str2);
                return;
            case 'R':
                this.mTarget.onSeatBackrestForemost(str, str2);
                return;
            case 'S':
                this.mTarget.onWindowsRightOpen(str, str2);
                return;
            case 'T':
                this.mTarget.onEnergyRecycleUp(str, str2);
                return;
            case 'U':
                this.mTarget.onSeatMoveHighest(str, str2);
                return;
            case 'V':
                this.mTarget.onControlElectricCurtainClose(str, str2);
                return;
            case 'W':
                this.mTarget.onWindowRightRearOpen(str, str2);
                return;
            case 'X':
                this.mTarget.onLightAtmosphereColor(str, str2);
                return;
            case 'Y':
                this.mTarget.onWindowsVentilateOn(str, str2);
                return;
            case 'Z':
                this.mTarget.onLegLowest(str, str2);
                return;
            case '[':
                this.mTarget.onLightLowOn(str, str2);
                return;
            case '\\':
                this.mTarget.onLeftChargePortOpen(str, str2);
                return;
            case ']':
                this.mTarget.onSeatMoveLowest(str, str2);
                return;
            case '^':
                this.mTarget.onTrunkOpen(str, str2);
                return;
            case '_':
                this.mTarget.onWindowsVentilateOff(str, str2);
                return;
            case '`':
                this.mTarget.onControlLightResume(str, str2);
                return;
            case 'a':
                this.mTarget.onWindowsRightClose(str, str2);
                return;
            case 'b':
                this.mTarget.onWindowsLeftOpen(str, str2);
                return;
            case 'c':
                this.mTarget.onControlXpedalOff();
                return;
            case 'd':
                this.mTarget.onControlXpedalOn();
                return;
            case 'e':
                this.mTarget.onLightAutoOff(str, str2);
                return;
            case 'f':
                this.mTarget.onModesSteeringSport(str, str2);
                return;
            case 'g':
                this.mTarget.onPsnSeatMoveBack();
                return;
            case 'h':
                this.mTarget.onPsnSeatMoveDown();
                return;
            case 'i':
                this.mTarget.onModesSteeringSoft(str, str2);
                return;
            case 'j':
                this.mTarget.onMirrorRearSet(str, str2);
                return;
            case 'k':
                this.mTarget.onModesDrivingNormal(str, str2);
                return;
            case 'l':
                this.mTarget.onWindowFrontOpen(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CarcontrolEvent.LIGHT_HOME_OFF, CarcontrolEvent.LIGHT_HOME_ON, CarcontrolEvent.LIGHT_LOW_OFF, CarcontrolEvent.LIGHT_LOW_ON, CarcontrolEvent.LIGHT_POSITION_ON, CarcontrolEvent.LIGHT_POSITION_OFF, CarcontrolEvent.LIGHT_AUTO_ON, CarcontrolEvent.LIGHT_AUTO_OFF, CarcontrolEvent.MIST_LIGHT_OFF, CarcontrolEvent.MIST_LIGHT_ON, CarcontrolEvent.MIRROR_REAR_CLOSE, CarcontrolEvent.MIRROR_REAR_ON, CarcontrolEvent.TRUNK_OPEN, CarcontrolEvent.WINDOW_DRIVER_CLOSE, CarcontrolEvent.WINDOW_DRIVER_OPEN, CarcontrolEvent.WINDOW_PASSENGER_CLOSE, CarcontrolEvent.WINDOW_PASSENGER_OPEN, CarcontrolEvent.WINDOWS_CLOSE, CarcontrolEvent.WINDOWS_OPEN, CarcontrolEvent.TRUNK_CLOSE, CarcontrolEvent.WINDOWS_VENTILATE_ON, CarcontrolEvent.WINDOWS_VENTILATE_OFF, CarcontrolEvent.MODES_DRIVING_SPORT, CarcontrolEvent.MODES_DRIVING_CONSERVATION, CarcontrolEvent.MODES_DRIVING_NORMAL, CarcontrolEvent.MODES_STEERING_SOFT, CarcontrolEvent.MODES_STEERING_NORMAL, CarcontrolEvent.MODES_STEERING_SPORT, CarcontrolEvent.ENERGY_RECYCLE_HIGH, CarcontrolEvent.ENERGY_RECYCLE_LOW, CarcontrolEvent.ENERGY_RECYCLE_MEDIA, CarcontrolEvent.ENERGY_RECYCLE_UP, CarcontrolEvent.ENERGY_RECYCLE_DOWN, CarcontrolEvent.WINDOW_RIGHT_REAR_OPEN, CarcontrolEvent.WINDOW_RIGHT_REAR_CLOSE, CarcontrolEvent.WINDOW_LEFT_REAR_OPEN, CarcontrolEvent.WINDOW_LEFT_REAR_CLOSE, CarcontrolEvent.WINDOW_FRONT_OPEN, CarcontrolEvent.WINDOW_FRONT_CLOSE, CarcontrolEvent.WINDOW_REAR_OPEN, CarcontrolEvent.WINDOW_REAR_CLOSE, CarcontrolEvent.SEAT_MOVE_UP, CarcontrolEvent.SEAT_MOVE_DOWN, CarcontrolEvent.SEAT_MOVE_HIGHEST, CarcontrolEvent.SEAT_MOVE_LOWEST, CarcontrolEvent.SEAT_MOVE_BACK, CarcontrolEvent.SEAT_MOVE_FORWARD, CarcontrolEvent.SEAT_MOVE_REAR, CarcontrolEvent.SEAT_MOVE_FOREMOST, CarcontrolEvent.SEAT_BACKREST_BACK, CarcontrolEvent.SEAT_BACKREST_FORWARD, CarcontrolEvent.SEAT_BACKREST_REAR, CarcontrolEvent.SEAT_BACKREST_FOREMOST, CarcontrolEvent.SEAT_ADJUST, CarcontrolEvent.CONTROL_LIGHT_RESUME, CarcontrolEvent.CONTROL_SEAT_RESUME, CarcontrolEvent.LOW_VOLUME_ON, CarcontrolEvent.LOW_VOLUME_OFF, CarcontrolEvent.LIGHT_ATMOSPHERE_ON, CarcontrolEvent.LIGHT_ATMOSPHERE_OFF, CarcontrolEvent.MIRROR_REAR_SET, CarcontrolEvent.WIPER_SPEED_UP, CarcontrolEvent.WIPER_SPEED_DOWN, CarcontrolEvent.WIPER_SLOW, CarcontrolEvent.WIPER_HIGH, CarcontrolEvent.WIPER_MEDIUM, CarcontrolEvent.WIPER_SUPERHIGH, CarcontrolEvent.TRUNK_UNLOCK, CarcontrolEvent.WINDOW_LEFT_CLOSE, CarcontrolEvent.WINDOW_LEFT_OPEN, CarcontrolEvent.WINDOW_RIGHT_CLOSE, CarcontrolEvent.WINDOW_RIGHT_OPEN, CarcontrolEvent.LEG_UP, CarcontrolEvent.LEG_DOWN, CarcontrolEvent.LEG_HIGHEST, CarcontrolEvent.LEG_LOWEST, CarcontrolEvent.SEAT_RESTORE, CarcontrolEvent.DIRECT_PORT_ON, CarcontrolEvent.DIRECT_PORT_OFF, CarcontrolEvent.ALTERNATING_PORT_OFF, CarcontrolEvent.ALTERNATING_PORT_ON, CarcontrolEvent.TIRE_PRESSURE_SHOW, CarcontrolEvent.LIGHT_ATMOSPHERE_COLOR, CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_SET, CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_UP, CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_DOWN, CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MAX, CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MIN, CarcontrolEvent.PSN_SEAT_MOVE_UP, CarcontrolEvent.PSN_SEAT_MOVE_DOWN, CarcontrolEvent.PSN_SEAT_BACKREST_BACK, CarcontrolEvent.PSN_SEAT_BACKREST_FORWARD, CarcontrolEvent.PSN_SEAT_MOVE_BACK, CarcontrolEvent.PSN_SEAT_MOVE_FORWARD, CarcontrolEvent.CONTROL_XPEDAL_ON, CarcontrolEvent.CONTROL_XPEDAL_OFF, CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_ON, CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_ON, CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_OFF, CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_OFF, CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_PAUSE, CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_PAUSE, CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_OPEN, CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_CLOSE, CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_OPEN, CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_CLOSE, CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_ON, CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_OFF, CarcontrolEvent.CONTROL_CAPSULE_UNIVERSAL_SET};
    }
}
