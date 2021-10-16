package net.flow9.googlemapsclustering.data

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Row(
    val ADRES: String,
    val CHARGER_EMAIL: String,
    val CODE_VALUE: String,
    val FDRM_CLOSE_DATE: String = "",
    val FLOOR_DC: String,
    val FOND_YEAR: String,
    val FXNUM: String,
    val GU_CODE: String,
    val HMPG_URL: String,
    val LBRRY_INTRCN: String,
    val LBRRY_NAME: String,
    val LBRRY_SEQ_NO: String,
    val LBRRY_SE_NAME: String,
    val LON_GDCC: String,
    val MBER_SBSCRB_RQISIT: String,
    val OP_TIME: String,
    val TEL_NO: String,
    val TFCMN: String,
    val XCNTS: String,
    val YDNTS: String

    // 2. 데이터 클래스에 ClusterItem 을 추가하고 필수 메서드를 오버라이드
) : ClusterItem {

    override fun getPosition(): LatLng { // 개별 마커가 표시될 좌표
        return LatLng(XCNTS.toDouble(), YDNTS.toDouble())
    }

    override fun getTitle(): String? { // 마커를 클릭했을 때 나타나는 타이틀
        return LBRRY_NAME
    }

    override fun getSnippet(): String? { // 마커를 클릭했을 때 나타나는 서브타이틀
        return ADRES
    }

    // 2.1 값 중에 null 이 있을 경우 hashCode 생성시 오류 발생
    //     id 에 해당하는 유일한 값을 Int로 반환하면 된다.
    override fun hashCode(): Int {
        return LBRRY_SEQ_NO.toInt()
    }
}