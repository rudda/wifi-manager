package br.dev.app.wifi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.dev.app.wifi.R;
import br.dev.app.wifi.domain.wifi.Wifi;

/**
 * Created by jerab on 21/03/18.
 */

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.holder>{

    private List<Wifi> wifiList;
    private Context context;
    onWifiClick listener;

    public interface  onWifiClick{

        public void cadastrarWifi(Wifi w);

    }

    public WifiListAdapter(List<Wifi> wifiList, Context context, onWifiClick listener) {
        this.wifiList = wifiList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.layout_wifi_item, null, false);

        return new holder(v);
    }

    @Override
    public void onBindViewHolder(holder holder, final int position) {

        holder.tv_wifi_name.setText(wifiList.get(position).getSSID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            listener.cadastrarWifi(wifiList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.wifiList.size();
    }

    class holder extends RecyclerView.ViewHolder{


        public TextView tv_wifi_name;
        public holder(View itemView) {
            super(itemView);

            tv_wifi_name = (TextView) itemView.findViewById(R.id.tvf_wifi_name);

        }
    }

}
