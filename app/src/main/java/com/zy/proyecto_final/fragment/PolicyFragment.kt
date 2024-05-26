import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zy.proyecto_final.R
import com.zy.proyecto_final.fragment.SettingFragment

class PolicyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_policy, container, false)
        view.findViewById<ImageView>(R.id.back)!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, SettingFragment())?.commit()
        }
        val tvPolicyContent = view.findViewById<TextView>(R.id.tvPolicyContent)
        val policyContent = getString(R.string.policy_content)

        // Convierte el contenido HTML a texto formateado
        tvPolicyContent.text = Html.fromHtml(policyContent)

        return view
    }
}
